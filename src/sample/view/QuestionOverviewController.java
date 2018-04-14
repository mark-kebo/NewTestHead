package sample.view;

import javafx.scene.control.Alert;
import sample.MainApp;
import sample.model.Question;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class QuestionOverviewController {
    @FXML
    private TableView<Question> questionTable;
    @FXML
    private TableColumn<Question, String> questionsNameColumn;

    @FXML
    private Label questionNameLabel;
    @FXML
    private Label questionFirstLabel;
    @FXML
    private Label questionSecondLabel;
    @FXML
    private Label questionThirdLabel;
    @FXML
    private Label questionFourthLabel;
    @FXML
    private Label correctQuestionLabel;

    // Reference to the main application.
    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public QuestionOverviewController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        questionsNameColumn.setCellValueFactory(
                cellData -> cellData.getValue().questionNameProperty());

        // Clear person details.
        showQuestionDetails(null);

        // Listen for selection changes and show the person details when changed.
        questionTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showQuestionDetails(newValue));
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        questionTable.setItems(mainApp.getQuestionData());
    }

    /**
     * Fills all text fields to show details about the question.
     * If the specified question is null, all text fields are cleared.
     *
     * @param question the question or null
     */
    private void showQuestionDetails(Question question) {
        if (question != null) {
            // Fill the labels with info from the question object.
            questionNameLabel.setText(question.getQuestionName());
            questionFirstLabel.setText(question.getQuestionFirst());
            questionSecondLabel.setText(question.getQuestionSecond());
            questionThirdLabel.setText(question.getQuestionThird());
            questionFourthLabel.setText(question.getQuestionFourth());
            correctQuestionLabel.setText(Integer.toString(question.getCorrectQuestion()));
        } else {
            // Question is null, remove all the text.
            questionNameLabel.setText("");
            questionFirstLabel.setText("");
            questionSecondLabel.setText("");
            questionThirdLabel.setText("");
            questionFourthLabel.setText("");
            correctQuestionLabel.setText("");
        }
    }

    /**
     * Called when the user clicks on the delete button.
     */
    @FXML
    private void handleDeleteQuestion() {
        int selectedIndex = questionTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            questionTable.getItems().remove(selectedIndex);
        } else {
            // Nothing selected.
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Не выбранно вопроса");
            alert.setHeaderText("Не выбранно вопроса");
            alert.setContentText("Пожалуйста, выберите вопрос из таблицы");

            alert.showAndWait();
        }
    }

    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new question.
     */
    @FXML
    private void handleNewQuestion() {
        Question tempQuestion = new Question();
        boolean okClicked = mainApp.showQuestionEditDialog(tempQuestion);
        if (okClicked) {
            mainApp.getQuestionData().add(tempQuestion);
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected question.
     */
    @FXML
    private void handleEditQuestion() {
        Question selectedQuestion = questionTable.getSelectionModel().getSelectedItem();
        if (selectedQuestion != null) {
            boolean okClicked = mainApp.showQuestionEditDialog(selectedQuestion);
            if (okClicked) {
                showQuestionDetails(selectedQuestion);
            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Не выбранно вопроса");
            alert.setHeaderText("Не выбранно вопроса");
            alert.setContentText("Пожалуйста, выберите вопрос из таблицы");

            alert.showAndWait();
        }
    }
}