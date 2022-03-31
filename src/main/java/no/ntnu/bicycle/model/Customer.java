package no.ntnu.bicycle.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;

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

    public Customer(int id, String name, String email, LocalDate dob, Integer age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.age = age;
    }

    public Customer(String name, String email, LocalDate dob, Integer age) {
        this.id = 0;
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.age = age;
    }

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
