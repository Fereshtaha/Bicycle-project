package no.ntnu.bicycle.model;

import javax.persistence.*;

@Entity
@Table(name = "address")
public class BillingAndShippingAddress {
    @Id
    @GeneratedValue
    private long id;
    private String firstName;
    private String lastName;
    private String address;
    private String country;
    private String postalCode;
    private String city;
    //@OneToOne(mappedBy = "address")
    //private Customer customer;

    public BillingAndShippingAddress(String firstName, String lastName, String address, String country, String postalCode, String city) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.country = country;
        this.postalCode = postalCode;
        this.city = city;
    }

    public BillingAndShippingAddress() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostal(){
        return postalCode + " " + city;
    }
}
