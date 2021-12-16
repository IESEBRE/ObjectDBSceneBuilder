package com.iesebre.provascenebuilder.model;

import javafx.beans.property.*;

import java.time.LocalDate;

import javax.persistence.*;
/**
 * Model class for a Person.
 *
 * @author Marco Jakob
 */
public class Person{

    //Enumeració declarada com a estàtica
    public static enum City {
        TORTOSA("Tortosa"), REUS("Reus"), PARIS("París"), LONDRES("Londres");

        City(String name) {
            this.name = name;
        }

        private String name;

        public String getName() {
            return name;
        }

        public String toString(){return getName();}
    }

    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty street;
    private final IntegerProperty postalCode;
    private final FloatProperty sou;
    private final ObjectProperty<LocalDate> birthday;
    private final ObjectProperty<City> city;

    /**
     * Default constructor.
     */
    public Person() {
        this("some first name", "some last name");
    }

    /**
     * Constructor with some initial data.
     *
     * @param firstName
     * @param lastName
     */
    public Person(String firstName, String lastName) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);

        // Some initial dummy data, just for convenient testing.
        this.street = new SimpleStringProperty("some street");
        this.postalCode = new SimpleIntegerProperty(1234);
        this.sou = new SimpleFloatProperty(1234.56f);
        this.city = new SimpleObjectProperty<City>(City.TORTOSA);
        this.birthday = new SimpleObjectProperty<LocalDate>(LocalDate.of(1999, 2, 21));
    }

    public Float getSou() {
        return sou.get();
    }

    public FloatProperty souProperty() {
        return sou;
    }

    public void setSou(float sou) {
        this.sou.set(sou);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getFirstNameString() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public String getStreet() {
        return street.get();
    }

    public void setStreet(String street) {
        this.street.set(street);
    }

    public StringProperty streetProperty() {
        return street;
    }

    public int getPostalCode() {
        return postalCode.get();
    }

    public void setPostalCode(int postalCode) {
        this.postalCode.set(postalCode);
    }

    public IntegerProperty postalCodeProperty() {
        return postalCode;
    }

    public City getCity() {
        return city.get();
    }

    public void setCity(City city) {
        this.city.set(city);
    }

    public ObjectProperty<City> cityProperty() {
        return city;
    }

    public LocalDate getBirthday() {
        return birthday.get();
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday.set(birthday);
    }

    public ObjectProperty<LocalDate> birthdayProperty() {
        return birthday;
    }

//    public static void main(String[] args) {
//        System.out.println(City.TORTOSA);
//    }
}

