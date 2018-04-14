package sample.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for a Person.
 *
 * @author Dmitry Vorozhbicky
 */
public class Person {
    private final StringProperty personName;
    private final StringProperty personCorrect;
    private final StringProperty personPercent;

    /**
     * Default constructor.
     */
    public Person() {
        this(null, null, null);
    }

    public String getPersonName() {
        return personName.get();
    }

    public StringProperty personNameProperty() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName.set(personName);
    }

    public String getPersonCorrect() {
        return personCorrect.get();
    }

    public StringProperty personCorrectProperty() {
        return personCorrect;
    }

    public void setPersonCorrect(String personCorrect) {
        this.personCorrect.set(personCorrect);
    }

    public String getPersonPercent() {
        return personPercent.get();
    }

    public StringProperty personPercentProperty() {
        return personPercent;
    }

    public void setPersonPercent(String personPercent) {
        this.personPercent.set(personPercent);
    }

    /**
     * Constructor with some initial data.
     *
     * @param personName
     * @param personCorrect
     * @param personPercent
     */
    public Person(String personName, String personCorrect, String personPercent) {
        this.personName = new SimpleStringProperty(personName);
        this.personCorrect = new SimpleStringProperty(personCorrect);
        this.personPercent = new SimpleStringProperty(personPercent);
    }
}
