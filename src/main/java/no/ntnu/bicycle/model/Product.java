package no.ntnu.bicycle.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue
    private int id;
    private String productName;
    private String imageUrl;
    private String color;
    private String description;
    private int price;

    @ManyToMany(mappedBy = "shoppingCart")
    @JsonIgnore
    private List<Customer> customer;

    public Product(String productName, String color, String imageUrl,String description, int price) {
        this.productName = productName;
        this.color = color;
        this.imageUrl = imageUrl;
        this.description = description;
        this.price = price;
    }

    public Product() {
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
