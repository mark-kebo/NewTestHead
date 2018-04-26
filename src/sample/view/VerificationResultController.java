package sample.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.model.Person;
import sample.model.PersonListWrapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.prefs.Preferences;

public class VerificationResultController {
    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person, String> personNameColumn;
    @FXML
    private TableColumn<Person, String> personPercentColumn;
    @FXML
    private TableColumn<Person, String> personCorrectColumn;

    private Stage dialogStage;
    private ObservableList<Person> personData = FXCollections.observableArrayList();

    /**
     * Инициализирует корневой макет и пытается загрузить последний открытый
     * файл с адресатами.
     */
    public void initVRCLayout() {
        // Пытается загрузить последний открытый файл с адресатами.
        File file = getPersonFilePath();
        if (file != null) {
            loadPersonDataFromFile(file);
        }
    }

    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        personNameColumn.setCellValueFactory(
                cellData -> cellData.getValue().personNameProperty());
        personPercentColumn.setCellValueFactory(
                cellData -> cellData.getValue().personPercentProperty());
        personCorrectColumn.setCellValueFactory(
                cellData -> cellData.getValue().personCorrectProperty());
        personCorrectColumn.setCellFactory(param -> {
            TableCell<Person, String> cell = new TableCell<>();
            Text text = new Text();
            text.setStyle("");
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            //text.wrappingWidthProperty().bind(cell.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            text.wrappingWidthProperty().bind(personCorrectColumn.widthProperty());
            return cell;
        });
    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Sets the person to be edited in the dialog.
     */
    public void setPerson() {
        personTable.setItems(getPersonData());
    }

    private ObservableList<Person> getPersonData() {
        return personData;
    }

    /**
     * Закрывает окно.
     */
    @FXML
    private void handleExit() {
        dialogStage.close();
    }

    /**
     * Очистка списка.
     */
    @FXML
    private void handleClear() {
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            personTable.getItems().remove(selectedIndex);
        } else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(dialogStage);
            alert.setTitle("Не выбран человек");
            alert.setHeaderText("Не выбран человек, нечего удалять");
            alert.setContentText("Пожалуйста, выберите человека из таблицы");

            alert.showAndWait();
        }
    }

    /**
     * Print.
     */
    @FXML
    private void handlePrint() {
        print(personTable);
    }

    private void print(final Node node) {
        Printer printer = Printer.getDefaultPrinter();

        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            boolean success = job.printPage(node);
            if (success) {
                System.out.println("PRINT");
                job.endJob();
            }
        }
    }

    /**
     * Returns the person file preference, i.e. the file that was last opened.
     * The preference is read from the OS specific registry. If no such
     * preference can be found, null is returned.
     *
     * @return
     */
    public File getPersonFilePath() {
        System.out.println("getPersonFilePath()");
        Preferences prefs = Preferences.userNodeForPackage(VerificationResultController.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    /**
     * Sets the file path of the currently loaded file. The path is persisted in
     * the OS specific registry.
     *
     * @param file the file or null to remove the path
     */
    public void setPersonFilePath(File file) {
        System.out.println("setPersonFilePath(File file)");
        Preferences prefs = Preferences.userNodeForPackage(VerificationResultController.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            // Update the stage title.
            dialogStage.setTitle("Результаты тестирования - " + file.getName());
        } else {
            prefs.remove("filePath");

            // Update the stage title.
            dialogStage.setTitle("Результаты тестирования");
        }
    }

    /**
     * Загружает информацию об адресатах из указанного файла.
     * Текущая информация об адресатах будет заменена.
     *
     * @param file
     */
    public void loadPersonDataFromFile(File file) {
        try {
            System.out.println("loadPersonDataFromFile(File file)");
            JAXBContext context = JAXBContext
                    .newInstance(PersonListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Чтение XML из файла и демаршализация.
            PersonListWrapper wrapper = (PersonListWrapper) um.unmarshal(file);

            personData.clear();
            personData.addAll(wrapper.getPersons());

            // Сохраняем путь к файлу в реестре.
            setPersonFilePath(file);

        } catch (Exception e) { // catches ANY exception
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Невозможно загрузить файл");
            alert.setContentText("Невозможно загрузить информацию из файла:\n" + file.getPath());

            alert.showAndWait();
        }
    }

    /**
     * Сохраняет текущую информацию об адресатах в указанном файле.
     *
     * @param file
     */
    public void savePersonDataToFile(File file) {
        try {
            System.out.println("savePersonDataToFile(File file)");
            JAXBContext context = JAXBContext
                    .newInstance(PersonListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Обёртываем наши данные об адресатах.
            PersonListWrapper wrapper = new PersonListWrapper();
            wrapper.setPersons(personData);

            // Маршаллируем и сохраняем XML в файл.
            m.marshal(wrapper, file);

            // Сохраняем путь к файлу в реестре.
            setPersonFilePath(file);
        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Невозможно сохранить файл");
            alert.setContentText("Невозможно сохранить информацию в файл:\n" + file.getPath());

            alert.showAndWait();
        }
    }

    /**
     * Открывает FileChooser, чтобы пользователь имел возможность
     * выбрать адресную книгу для загрузки.
     */
    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();

        // Задаём фильтр расширений
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Показываем диалог загрузки файла
        File file = fileChooser.showOpenDialog(dialogStage);

        if (file != null) {
            loadPersonDataFromFile(file);
        }
    }

    /**
     * Сохраняет файл в файл адресатов, который в настоящее время открыт.
     * Если файл не открыт, то отображается диалог "save as".
     */
    @FXML
    private void handleSave() {
        File personFile = getPersonFilePath();
        if (personFile != null) {
            savePersonDataToFile(personFile);
        } else {
            handleSaveAs();
        }
    }

    /**
     * Открывает FileChooser, чтобы пользователь имел возможность
     * выбрать файл, куда будут сохранены данные
     */
    @FXML
    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();

        // Задаём фильтр расширений
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Показываем диалог сохранения файла
        File file = fileChooser.showSaveDialog(dialogStage);

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            savePersonDataToFile(file);
        }
    }
}
