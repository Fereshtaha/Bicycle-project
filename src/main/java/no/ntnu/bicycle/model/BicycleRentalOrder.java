package no.ntnu.bicycle.model;

import javax.persistence.*;
import java.awt.*;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * The type Bicycle rental order.
 */
@Entity
public class BicycleRentalOrder {
    @Id
    @GeneratedValue
    private long id;
    @OneToOne
    @JoinColumn(name = "bicycle_id", referencedColumnName = "id")
    private Bicycle bicycle;
    @OneToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;
    private String location;
    private LocalDateTime rentalStartTime;
    private LocalDateTime rentalEndTime;
    private int pricePerMinute;
    private int totalPrice;

    /**
     * Gets bicycle.
     *
     * @return the bicycle
     */
    public Bicycle getBicycle() {
        return bicycle;
    }

    /**
     * Gets customer.
     *
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Instantiates a new Bicycle rental order.
     *
     * @param bicycle    the bicycle
     * @param customer   the customer
     * @param location   the location
     * @param pricePerMinute the price per minute
     */
    public BicycleRentalOrder(Bicycle bicycle, Customer customer, String location, int pricePerMinute) {
        this.bicycle = bicycle;
        this.customer = customer;
        this.location = location;
        this.rentalStartTime = LocalDateTime.now();
        this.rentalEndTime = null;
        this.pricePerMinute = pricePerMinute;
        this.totalPrice = 0;
    }

    /**
     * Instantiates a new Bicycle rental order.
     */
    public BicycleRentalOrder() {

    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Get elapsed time in minutes long.
     *
     * @return the long
     */
    public Long getElapsedTimeInMinutes(){
        return Duration.between(rentalStartTime,LocalDateTime.now()).toMinutes();
    }

    /**
     * Gets location.
     *
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets location.
     *
     * @param location the location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets rental start time.
     *
     * @return the rental start time
     */
    public LocalDateTime getRentalStartTime() {
        return rentalStartTime;
    }

    /**
     * Sets rental start time.
     *
     * @param rentalStartTime the rental start time
     */
    public void setRentalStartTime(LocalDateTime rentalStartTime) {
        this.rentalStartTime = rentalStartTime;
    }

    /**
     * Gets rental end time.
     *
     * @return the rental end time
     */
    public LocalDateTime getRentalEndTime() {
        return rentalEndTime;
    }


    public void setId(long id) {
        this.id = id;
    }

    public void setBicycle(Bicycle bicycle) {
        this.bicycle = bicycle;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * Sets rental end time.
     *
     * @param rentalEndTime the rental end time
     */
    public void setRentalEndTime(LocalDateTime rentalEndTime) {
        this.rentalEndTime = rentalEndTime;
    }

    /**
     * Gets total price.
     *
     * @return the total price
     */
    public int getPricePerMinute() {
        return pricePerMinute;
    }

    /**
     * Sets total price.
     *
     * @param totalPrice the total price
     */
    public void setPricePerMinute(int totalPrice) {
        this.pricePerMinute = totalPrice;
    }
}
