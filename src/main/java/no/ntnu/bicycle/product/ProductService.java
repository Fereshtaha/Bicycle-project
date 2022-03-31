package no.ntnu.bicycle.product;

import no.ntnu.bicycle.product.Product;
import no.ntnu.bicycle.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public static <T> List<T> iterableToList(Iterable<T> iterable) {
        List<T> list = new LinkedList<>();
        iterable.forEach(list::add);
        return list;
    }

    public List<Product> getAllProducts() {return iterableToList(productRepository.findAll());}
}

