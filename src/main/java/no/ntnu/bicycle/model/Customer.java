package no.ntnu.bicycle.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;

@Entity
public class Customer {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String email;
    private LocalDate dob;
    private Integer age;

    //@OneToMany
    //@JsonIgnore
    //private Set<CustomerOrder> orders = new HashSet<>();

    public Customer() {

    }

    public Customer(String name, String email, LocalDate dob) {
        this.name = name;
        this.email = email;
        this.dob = dob;
        LocalDate today = LocalDate.now(); // Today's date is 10th Jan 2022
        Period p = Period.between(dob, today);
        this.age = p.getYears();

    }

    /**
     * Getters and setters
     * @return values
     */

    @JsonIgnore
    public boolean isValid() {
        return !"".equals(name) && age >0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", dob=" + dob +
                ", age=" + age +
                '}';
    }

    /**
     * Adding an order to all the orders
     * @param order the order to add
     */

    //public void addOrder(CustomerOrder order) {
    //    orders.add(order);
    //}
}
