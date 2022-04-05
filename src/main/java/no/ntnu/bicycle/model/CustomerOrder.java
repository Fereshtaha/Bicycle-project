package no.ntnu.bicycle.model;

import javax.persistence.*;

@Entity
public class CustomerOrder {

    @Id
    @GeneratedValue
    private int id;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
