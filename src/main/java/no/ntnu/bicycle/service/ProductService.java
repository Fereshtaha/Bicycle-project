package no.ntnu.bicycle.service;

import no.ntnu.bicycle.model.Product;
import no.ntnu.bicycle.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * Business logic related to products
 */
@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    /**
     * Constructor for product service
     * @param productRepository ProductService
     */
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Makes iterable to list of products
     * @param iterable Iterable<Product>
     * @return list of products
     */
    public List<Product> iterableToList(Iterable<Product> iterable) {
        List<Product> list = new LinkedList<>();
        iterable.forEach(list::add);
        return list;
    }

    /**
     * Gets all products
     * @return list of products
     */
    public List<Product> getAllProducts() {return iterableToList(productRepository.findAll());}

    /**
     * Finds order by ID
     * @param id Integer
     * @return The author or null if none found by that ID
     */
    public Product findOrderById(Integer id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> getAllProductsByCustomer(String customer) {
        return null;
    }

    public List<Product> getAllProductsByOrder(String order) {
        return null;
    }

    public List<Product> getAllProductsByCustomerAndOrder(String customer, String order) {
        return null;
    }

}

