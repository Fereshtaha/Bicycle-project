package no.ntnu.bicycle.repository;

import no.ntnu.bicycle.model.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<CustomerOrder, Integer> {
   // @Query(value = "SELECT * FROM public.order where firstname = :firstname", nativeQuery = true)
   // List<CustomerOrder> findByCustomerName(String customer);

   // @Query(value = "SELECT * FROM public.order where productName = :productName", nativeQuery = true)
   // List<CustomerOrder> findByProductName(String product);

   /// @Query(value = "SELECT * ")
   // List<CustomerOrder> findByCustomerNameAndProduct(String customer, String product);

    //List<CustomerOrder> findAllByCustomer_ID(int customerId);

}
