package no.ntnu.bicycle.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bicycle")
public class Bicycle {
    @Id
    @GeneratedValue
    private int id;
    private String color;
    private int pricePerMinute;

    public Bicycle(String color, int pricePerMinute) {
        this.color = color;
        this.pricePerMinute = pricePerMinute;
    }

    public Bicycle() {

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
