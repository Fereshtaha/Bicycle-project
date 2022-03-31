package no.ntnu.bicycle.repository;

import no.ntnu.bicycle.model.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<CustomerOrder, Integer> {
    //List<CustomerOrder> findByCustomerName(String customer);
    //List<CustomerOrder> findByProductName(String product);
    //List<CustomerOrder> findByCustomerNameAndProduct(String customer, String product);
}
