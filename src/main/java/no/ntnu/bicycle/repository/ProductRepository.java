package no.ntnu.bicycle.repository;

import no.ntnu.bicycle.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Integer> {
}
