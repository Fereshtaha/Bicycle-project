package no.ntnu.bicycle.customer;

import no.ntnu.bicycle.model.Customer;
import no.ntnu.bicycle.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class CustomerConfig {

    @Bean
    CommandLineRunner commandLineRunner(CustomerRepository repository) {
        return args ->  {
            Customer janne = new Customer(
                    "Janne",
                    "janne@gmail.com",
                    LocalDate.of(1996, Month.MAY, 12),
                    25
            );

            Customer marius = new Customer(
                    "Marius",
                    "marius@gmail.com",
                    LocalDate.of(1997, Month.APRIL, 12),
                    24
            );

            repository.saveAll(
                    List.of(janne, marius));
    };
    }
}
