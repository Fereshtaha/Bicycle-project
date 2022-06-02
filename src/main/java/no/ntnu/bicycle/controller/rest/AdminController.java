package no.ntnu.bicycle.controller.rest;

import no.ntnu.bicycle.model.Bicycle;
import no.ntnu.bicycle.model.Customer;
import no.ntnu.bicycle.model.Product;
import no.ntnu.bicycle.service.BicycleRentalOrderService;
import no.ntnu.bicycle.service.BicycleService;
import no.ntnu.bicycle.service.CustomerService;
import no.ntnu.bicycle.service.ProductService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//!TODO is this correct?
@RequestMapping("api/admin")
public class AdminController {
    private CustomerService customerService;
    private ProductService productService;
    private BicycleService bicycleService;
    private BicycleRentalOrderService bicycleRentalOrderService;

    /**
     * Constructor with parameters
     * @param customerService CustomerService
     * @param productService ProductService
     * @param bicycleService BicycleService
     * @param bicycleRentalOrderService BicycleRentalOrderService
     */
    public AdminController(CustomerService customerService,
                           ProductService productService,
                           BicycleService bicycleService,
                           BicycleRentalOrderService
                                   bicycleRentalOrderService) {
        this.customerService = customerService;
        this.productService = productService;
        this.bicycleService = bicycleService;
        this.bicycleRentalOrderService = bicycleRentalOrderService;
    }

    /**
     * Empty constructor
     */
    public AdminController() {
    }

    /**
     * Adds a new customer
     * @param customer Customer
     */
    public void addCustomer(Customer customer) {
        customerService.addNewCustomer(customer);
    }

    /**
     * Deletes a customer
     * @param id int
     */
    public void deleteCustomer(int id) {
        customerService.deleteCustomer(id);
    }

    /**
     * Updates customer
     * @param customerId long
     * @param customer Customer
     */
    public void updateCustomer(long customerId, Customer customer) {
        customerService.updateCustomer(customerId, customer);
    }

    /**
     * Adds a product
     * @param product Product
     */
    public void addProduct(Product product) {
        productService.addNewProduct(product);
    }

    /**
     * Deletes a product
     * @param product Product
     */
    public void deleteProduct(Product product) {
        productService.deletingProduct(product);
    }

    /**
     * Updates a product
     * @param id int
     * @param product Product
     */
    public void updateProduct(int id, Product product) {
        productService.updateProduct(id, product);
    }

    /**
     * Sets the status to rented
     * @param bicycle Bicycle. The bicycle that needs to be set as rented.
     * @return true if it is rented, false if it is available.
     */
    public boolean setBicycleToRented(Bicycle bicycle) {
        if (bicycle != null) {
            bicycleService.setStatusToRented(bicycle);
            return true;
        }
        return false;
    }

    /**
     * Deletes a bicycle
     * @param id int
     */
    public void deletingBicycle(int id) {
        bicycleService.deleteBicycle(id);
    }

}
