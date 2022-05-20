package no.ntnu.bicycle.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.awt.*;
import java.util.Objects;

@Entity
@Table(name = "bicycle")
public class Bicycle {
    @Id
    @GeneratedValue
    private long id;
    private String color;
    private String location;
    private int pricePerMinute;
    private String status;

    public Bicycle(String color, String location, int pricePerMinute) {
        this.color = color;
        this.pricePerMinute = pricePerMinute;
        this.location = location;
        this.status = "NEW";
    }

    public Bicycle() {

    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getId(){
        return this.id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getPricePerMinute() {
        return pricePerMinute;
    }

    public void setPricePerMinute(int pricePerMinute) {
        this.pricePerMinute = pricePerMinute;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isAvailable() {
        return Objects.equals(this.status, "AVAILABLE");
    }

    public void setStatusToAvailable() {
        this.status = "AVAILABLE";
    }

    public void setStatusToRented(){
        this.status = "RENTED";
    }

    public void setStatusToDisabled(){
        this.status = "DISABLED";
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
