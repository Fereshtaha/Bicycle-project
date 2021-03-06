package no.ntnu.bicycle.repository;

import no.ntnu.bicycle.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
/*
    @Query(value = "SELECT * FROM public.payment where customer_number = : customerNumber")
    Optional<Payment> findByCustomerNumber(@Param("customerNumber") String customerNumber);
*/

}
