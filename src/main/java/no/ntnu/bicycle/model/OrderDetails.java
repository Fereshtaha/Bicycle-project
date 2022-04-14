package no.ntnu.bicycle.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class OrderDetails {
    @Id
    @GeneratedValue
    private int orderNumber;
    private int productCode;
    private int quantityOrdered;
    private int priceEach;

    public OrderDetails() {
    }

    public OrderDetails(int orderNumber, int productCode, int quantityOrdered, int priceEach) {
        this.orderNumber = orderNumber;
        this.productCode = productCode;
        this.quantityOrdered = quantityOrdered;
        this.priceEach = priceEach;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getProductCode() {
        return productCode;
    }

    public void setProductCode(int productCode) {
        this.productCode = productCode;
    }

    public int getQuantityOrdered() {
        return quantityOrdered;
    }

    public void setQuantityOrdered(int quantityOrdered) {
        this.quantityOrdered = quantityOrdered;
    }

    public int getPriceEach() {
        return priceEach;
    }

    public void setPriceEach(int priceEach) {
        this.priceEach = priceEach;
    }
}
