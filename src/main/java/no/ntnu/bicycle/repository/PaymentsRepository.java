package no.ntnu.bicycle.repository;

import no.ntnu.bicycle.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentsRepository extends JpaRepository<Payment, Integer> {

}
