package sample.model;

import javafx.beans.property.*;

/**
 * Model class for a Question.
 *
 * @author Dmitry Vorozhbicky
 */
public class Question {

    private final StringProperty questionName;
    private final StringProperty questionFirst;
    private final StringProperty questionSecond;
    private final StringProperty questionThird;
    private final StringProperty questionFourth;
    private final IntegerProperty correctQuestion;

    /**
     * Default constructor.
     */
    public Question() {
        this(null);
    }

    /**
     * Constructor with some initial data.
     *
     * @param questionName
     */
    public Question(String questionName) {
        this.questionName = new SimpleStringProperty(questionName);
        questionFirst = new SimpleStringProperty("");
        questionSecond = new SimpleStringProperty("");
        questionThird = new SimpleStringProperty("");
        questionFourth = new SimpleStringProperty("");
        correctQuestion = new SimpleIntegerProperty(0);
    }

    public String getQuestionName() {
        return questionName.get();
    }

    public StringProperty questionNameProperty() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName.set(questionName);
    }

    public String getQuestionFirst() {
        return questionFirst.get();
    }

    public StringProperty questionFirstProperty() {
        return questionFirst;
    }

    public void setQuestionFirst(String questionFirst) {
        this.questionFirst.set(questionFirst);
    }

    public String getQuestionSecond() {
        return questionSecond.get();
    }

    public StringProperty questionSecondProperty() {
        return questionSecond;
    }

    public void setQuestionSecond(String questionSecond) {
        this.questionSecond.set(questionSecond);
    }

    public String getQuestionThird() {
        return questionThird.get();
    }

    public StringProperty questionThirdProperty() {
        return questionThird;
    }

    public void setQuestionThird(String questionThird) {
        this.questionThird.set(questionThird);
    }

    public String getQuestionFourth() {
        return questionFourth.get();
    }

    public StringProperty questionFourthProperty() {
        return questionFourth;
    }

    public void setQuestionFourth(String questionFourth) {
        this.questionFourth.set(questionFourth);
    }

    public int getCorrectQuestion() {
        return correctQuestion.get();
    }

    public IntegerProperty correctQuestionProperty() {
        return correctQuestion;
    }

    public void setCorrectQuestion(int correctQuestion) {
        this.correctQuestion.set(correctQuestion);
    }
}