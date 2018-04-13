package sample.view;

import javafx.scene.control.Alert;
import sample.model.Question;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Dialog to edit details of a question.
 *
 * @author Dmitry Vorozhbicky
 */
public class QuestionEditDialogController {

    @FXML
    private TextField questionNameField;
    @FXML
    private TextField questionFirstField;
    @FXML
    private TextField questionSecondField;
    @FXML
    private TextField questionThirdField;
    @FXML
    private TextField questionFourthField;
    @FXML
    private TextField correctQuestionField;


    private Stage dialogStage;
    private Question question;
    private boolean okClicked = false;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
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
     * Sets the question to be edited in the dialog.
     *
     * @param question
     */
    public void setQuestion(Question question) {
        this.question = question;

        questionNameField.setText(question.getQuestionName());
        questionFirstField.setText(question.getQuestionFirst());
        questionSecondField.setText(question.getQuestionSecond());
        questionThirdField.setText(question.getQuestionThird());
        questionFourthField.setText(question.getQuestionFourth());
        correctQuestionField.setText(Integer.toString(question.getCorrectQuestion()));
    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            question.setQuestionName(questionNameField.getText());
            question.setQuestionFirst(questionFirstField.getText());
            question.setQuestionSecond(questionSecondField.getText());
            question.setQuestionThird(questionThirdField.getText());
            question.setQuestionFourth(questionFourthField.getText());
            question.setCorrectQuestion(Integer.parseInt(correctQuestionField.getText()));

            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (questionNameField.getText() == null || questionNameField.getText().length() == 0) {
            errorMessage += "Некорректное имя вопроса\n";
        }
        if (questionFirstField.getText() == null || questionFirstField.getText().length() == 0) {
            errorMessage += "Не заполненно поле первого ответа\n";
        }
        if (questionSecondField.getText() == null || questionSecondField.getText().length() == 0) {
            errorMessage += "Не заполненно поле второго ответа\n";
        }

        if (questionThirdField.getText() == null || questionThirdField.getText().length() == 0) {
            errorMessage += "Не заполненно поле третьего ответа\n";
        }

        if (questionFourthField.getText() == null || questionFourthField.getText().length() == 0) {
            errorMessage += "Не заполненно поле четвертого ответа\n";
        }

        if (correctQuestionField.getText() == null || correctQuestionField.getText().length() == 0
                || Integer.parseInt(correctQuestionField.getText()) < 1 ||
                Integer.parseInt(correctQuestionField.getText()) > 4) {
            errorMessage += "Некорректный номер вопроса\n";
        } else {
            // try to parse the postal code into an int.
            try {
                Integer.parseInt(correctQuestionField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Только номер вопроса(целочисленное значение)\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Показываем сообщение об ошибке.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Ошибка заполнения");
            alert.setHeaderText("Пожалуйста, введите верное значение");
            alert.setContentText(errorMessage);

            alert.showAndWait();
            return false;
        }
    }
}