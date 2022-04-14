package no.ntnu.bicycle.repository;

import no.ntnu.bicycle.model.Customer;
import org.hibernate.sql.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {


    /**
     * Gets a usr that has this email
     * @param email email to look for
     * @return th euser that natcged this email
     */
    @Query(value = "SELECT * FROM 'customer' WHERE email = :email", nativeQuery = true)
    Optional<Customer> findByEmail(@Param("email") String email);

}
