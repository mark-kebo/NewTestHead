package sample.model;

import javafx.beans.property.IntegerProperty;
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
    private final IntegerProperty personPercent;

    /**
     * Default constructor.
     */
    public Person() {
        this(null);
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

    public int getPersonPercent() {
        return personPercent.get();
    }

    public IntegerProperty personPercentProperty() {
        return personPercent;
    }

    public void setPersonPercent(int personPercent) {
        this.personPercent.set(personPercent);
    }

    /**
     * Constructor with some initial data.
     *
     * @param personName
     */
    public Person(String personName) {
        this.personName = new SimpleStringProperty(personName);
        personCorrect = new SimpleStringProperty("");
        personPercent = new SimpleIntegerProperty(0);
    }
}
