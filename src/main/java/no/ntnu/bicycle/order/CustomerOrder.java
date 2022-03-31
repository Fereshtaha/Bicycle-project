package no.ntnu.bicycle.order;

import no.ntnu.bicycle.product.Product;
import no.ntnu.bicycle.customer.Customer;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class CustomerOrder {

    @Id
    @GeneratedValue
    private int id;

    private String email;

    @ManyToOne(targetEntity = Customer.class)
    @JoinColumn
    private Customer customer;


    @ManyToOne
    private Product product;

    public CustomerOrder() {
    }

    public CustomerOrder(Integer id, String email) {
        this.id = id;
        this.email = email;
    }

   // public Product getProduct() {return product;}

    //public void setProduct(Product product) {this.product = product;}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Customer getCustomers() {
        return customer;
    }

    public void setCustomers(Customer customers) {
        this.customer = customers;
    }

}
