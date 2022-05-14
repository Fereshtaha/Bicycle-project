package no.ntnu.bicycle.model;

import javax.persistence.*;
import java.awt.*;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity
public class BicycleRentalOrder {
    @Id
    @GeneratedValue
    private int id;
    @OneToOne
    @JoinColumn(name = "bicycle_id", referencedColumnName = "id")
    private Bicycle bicycle;
    @OneToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;
    private Point location;
    private LocalDateTime rentalStartTime;
    private LocalDateTime rentalEndTime;
    private int totalPrice;

    public Bicycle getBicycle() {
        return bicycle;
    }

    public Customer getCustomer() {
        return customer;
    }

    public BicycleRentalOrder(Bicycle bicycle, Customer customer, Point location, int totalPrice) {
        this.bicycle = bicycle;
        this.customer = customer;
        this.location = location;
        this.rentalStartTime = LocalDateTime.now();
        this.rentalStartTime = null;
        this.totalPrice = totalPrice;
    }

    public BicycleRentalOrder() {

    }

    public int getId() {
        return id;
    }

    public Long getElapsedTimeInMinutes(){
        return Duration.between(rentalStartTime,LocalDateTime.now()).toMinutes();
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public LocalDateTime getRentalStartTime() {
        return rentalStartTime;
    }

    public void setRentalStartTime(LocalDateTime rentalStartTime) {
        this.rentalStartTime = rentalStartTime;
    }

    public LocalDateTime getRentalEndTime() {
        return rentalEndTime;
    }

    public void setRentalEndTime(LocalDateTime rentalEndTime) {
        this.rentalEndTime = rentalEndTime;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
