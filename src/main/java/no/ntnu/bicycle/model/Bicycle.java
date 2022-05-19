package no.ntnu.bicycle.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.awt.*;

@Entity
@Table(name = "bicycle")
public class Bicycle {
    @Id
    @GeneratedValue
    private int id;
    private String color;
    private String location;
    private int pricePerMinute;

    public Bicycle(String color, String location, int pricePerMinute) {
        this.color = color;
        this.pricePerMinute = pricePerMinute;
        this.location = location;
    }

    public Bicycle() {

    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getId(){
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
}
