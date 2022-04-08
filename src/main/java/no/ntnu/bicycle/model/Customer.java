package no.ntnu.bicycle.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity(name = "customers")

public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private LocalDate dob;
    private int phone;
    private Integer age;
    private boolean active = true;
    private String role;

    //@OneToMany
    //@JsonIgnore
    //private Set<CustomerOrder> orders = new HashSet<>();

    public Customer() {

    }

    public Customer(String firstName,String lastName,String email, String dob, int phone,String password,String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dob = LocalDate.parse(dob);
        this.phone = phone;
        this.password = password;
        this.role = role;
        LocalDate today = LocalDate.now(); // Today's date is 10th Jan 2022
        Period p = Period.between(LocalDate.parse(dob), today);
        this.age = p.getYears();
    }

    /**
     * Getters and setters
     * @return values
     */

    @JsonIgnore
    public boolean isValid() {
        return !"".equals(firstName) && age >0;
    }

    public int getId() {
        return id;
    }


    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = LocalDate.parse(dob);
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void updateAge(){
        LocalDate today = LocalDate.now();
        Period p = Period.between(this.dob, today);
        this.age = p.getYears();
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    /*@Override
    public String toString() {
        return "Customer{" +
                            "id=" + id +
                            ", firstName='" + firstName + '\'' +
                            ", lastName='" + lastName + '\'' +
                            ", email='" + email + '\'' +
                            ", dob=" + dob +
                            ", phone=" + phone +
                            ", age=" + age +
                            ", password=" + password +
                '}';
    }*/


    /**
     * Adding an order to all the orders
     * @param order the order to add
     */

    //public void addOrder(CustomerOrder order) {
    //    orders.add(order);
    //}
}
