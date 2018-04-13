package sample.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Вспомогательный класс для обёртывания списка.
 * Используется для сохранения списка в XML.
 *
 * @author Dmitry Vorozhbicky
 */
@XmlRootElement(name = "questions")
public class QuestionListWrapper {

    private List<Question> questions;

    @XmlElement(name = "question")
    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}