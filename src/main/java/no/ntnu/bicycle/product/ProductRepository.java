package no.ntnu.bicycle.product;

import no.ntnu.bicycle.product.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Integer> {
}
