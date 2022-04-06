package no.ntnu.bicycle.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class CustomerOrder {

    @Id
    @GeneratedValue
    private int id;

    private String email;
    private LocalDateTime dateAndTime;

    @ManyToOne(targetEntity = Customer.class)
    @JoinColumn
    private Customer customer;


    @ManyToOne
    private Product product;



    public CustomerOrder(Customer customer,Product product) {
        this.customer = customer;
        this.product = product;
    }

    public CustomerOrder() {
    }

    public CustomerOrder(Integer id, String email) {
        this.dateAndTime = LocalDateTime.now();
        this.id = id;
        this.email = email;
    }


    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(LocalDateTime dateAndTime) {
        this.dateAndTime = dateAndTime;
    }


     public Product getProduct() {return product;}

    public void setProduct(Product product) {this.product = product;}

    public Integer getId()
     {
        return id;
    }


}
