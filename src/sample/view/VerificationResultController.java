package sample.view;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import sample.model.Person;

public class VerificationResultController {
    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person, String> personNameColumn;
    @FXML
    private TableColumn<Person, Integer> personPercentColumn;
    @FXML
    private TableColumn<Person, String> personCorrectColumn;

    private Stage dialogStage;
    private Person person;
    private boolean okClicked = false;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        personNameColumn.setCellValueFactory(
                cellData -> cellData.getValue().personNameProperty());
        personPercentColumn.setCellValueFactory(
                cellData -> cellData.getValue().personPercentProperty());
        personCorrectColumn.setCellValueFactory(
                cellData -> cellData.getValue().personCorrectProperty());

        // Clear person details.
        showPersonDetails(null);

        // Listen for selection changes and show the person details when changed.
        personTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));
    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
}
