package no.ntnu.bicycle.model;



import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * The type customer order.
 * This will be added to the database.
 */
@Entity
public class CustomerOrder {

    @Id
    @GeneratedValue
    private int id;

    private String email;
    private LocalDateTime dateAndTime;

    @ManyToOne(targetEntity = Customer.class)
    @JoinColumn
    @Transient
    private Customer customer;


    //@ManyToOne
    @Transient
    private Product product;


    /**
     * Constructor with parameters.
     * @param customer the customer ordering
     * @param product the product
     */
    public CustomerOrder(Customer customer,Product product) {
        this.customer = customer;
        this.product = product;
    }

    /**
     * Empty constructor
     */
    public CustomerOrder() {
    }

    /**
     * Constructor with id and email
     * @param id id of the product
     * @param email customers email
     */
    public CustomerOrder(Integer id, String email) {
        this.dateAndTime = LocalDateTime.now();
        this.id = id;
        this.email = email;
    }

    /**
     * Gets local date and time
     * @return date and time
     */
    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    /**
     * Sets date and time
     * @param dateAndTime date and time to be set
     */
    public void setDateAndTime(LocalDateTime dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    /**
     * Gets product
     * @return product
     */
     public Product getProduct() {return product;}

    /**
     * Sets product
     * @param product product to be set
     */
    public void setProduct(Product product) {this.product = product;}

    /**
     * Gets id
     * @return id
     */
    public Integer getId()
     {
        return id;
    }


    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
