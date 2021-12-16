package com.iesebre.provascenebuilder.persistence.entities;

import com.iesebre.provascenebuilder.model.Person.City;

import javax.persistence.*;
import java.util.Date;

/**
 * Model class for a Person.
 *
 * @author Marco Jakob
 */
@Entity
public class Person {
    @Id @GeneratedValue
//    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @Enumerated(EnumType.ORDINAL) private City city;
    private String firstName;
    private String lastName;
    private String street;
    private Integer postalCode;
    private Float sou;
    @Temporal(TemporalType.DATE) private Date birthday;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
        this.firstName = firstName;
        this.lastName = lastName;

        // Some initial dummy data, just for convenient testing.
        this.street = "some street";
        this.postalCode = 1234;
        this.sou = 1234.56f;
        this.city = City.TORTOSA;
        this.birthday = new Date(1999, 2, 21);
    }

    public Float getSou() {
        return sou;
    }

    public void setSou(float sou) {
        this.sou=sou;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName=firstName;
    }

    public String getFirstNameString() {
        return firstName;
    }


    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName=lastName;
    }


    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street=street;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode=postalCode;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city=city;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday=birthday;
    }

}

