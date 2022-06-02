package no.ntnu.bicycle.repository;

import no.ntnu.bicycle.model.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Integer> {
    /*@Query(value = "SELECT * FROM public.order_details where order_number = :order_number")
    Optional<OrderDetails> findByOrderNumber(String orderNumber);

    @Query(value = "SELECT * FROM public.order_details where product_code = :product_code")
    Optional<OrderDetails> findByProductCode(String productCode);

    @Query(value = "SELECT * FROM public.order_details where price_each = :price_each")
    Optional<OrderDetails> findByPriceEach(String priceEach);*/
}
