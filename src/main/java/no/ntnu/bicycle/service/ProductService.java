package no.ntnu.bicycle.service;

import no.ntnu.bicycle.model.CustomerOrder;
import no.ntnu.bicycle.model.Product;
import no.ntnu.bicycle.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> iterableToList(Iterable<Product> iterable) {
        List<Product> list = new LinkedList<>();
        iterable.forEach(list::add);
        return list;
    }

    public List<Product> getAllProducts() {return iterableToList(productRepository.findAll());}

    public Product findOrderById(Integer id) {
        return productRepository.findById(id).orElse(null);
    }
}

