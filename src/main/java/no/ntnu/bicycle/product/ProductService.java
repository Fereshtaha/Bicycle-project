package no.ntnu.bicycle.product;

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
}

