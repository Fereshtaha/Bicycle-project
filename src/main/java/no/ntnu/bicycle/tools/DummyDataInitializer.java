package no.ntnu.bicycle.tools;

import no.ntnu.bicycle.model.Customer;
import no.ntnu.bicycle.model.CustomerOrder;
import no.ntnu.bicycle.model.Product;
import no.ntnu.bicycle.model.Role;
import no.ntnu.bicycle.repository.CustomerRepository;
import no.ntnu.bicycle.repository.OrderRepository;
import no.ntnu.bicycle.repository.ProductRepository;
import no.ntnu.bicycle.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.logging.Logger;

@Component
public class DummyDataInitializer implements ApplicationListener<ApplicationReadyEvent>{

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RoleRepository roleRepository;

    private final Logger logger = Logger.getLogger("DummyInit");

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (isDataImported()){
            logger.info("Data already exists");
            return;
        }
        logger.info("Importing data...");
        Customer sebastian = new Customer("Sebastian Nilsen","$2a$12$QjPXqckLsFqDDRxrEfboC.0WYcUSP5wMhuOftGkcnpA9vI1sUOiWa","sebasn@stud.ntnu.no", LocalDate.of(2001, Month.APRIL,19));
        Customer anne = new Customer("Anne Ruud", "$2a$12$QjPXqckLsFqDDRxrEfboC.0WYcUSP5wMhuOftGkcnpA9vI1sUOiWa","anne.ruud@mail.no",LocalDate.of(1994, Month.SEPTEMBER, 5));

        Role user = new Role("ROLE_USER");
        Role admin = new Role("ROLE_ADMIN");

        sebastian.addRole(admin);
        sebastian.addRole(user);
        anne.addRole(user);

        roleRepository.saveAll(List.of(user,admin));

        customerRepository.saveAll(List.of(sebastian,anne));

        Product blueHelmet = new Product("Blue helmet",199);
        Product whiteHelmet = new Product("White helmet",199);

        productRepository.saveAll(List.of(blueHelmet,whiteHelmet));

        CustomerOrder order1 = new CustomerOrder(sebastian,blueHelmet);
        CustomerOrder order2 = new CustomerOrder(anne,whiteHelmet);

        orderRepository.saveAll(List.of(order1,order2));



    }

    private boolean isDataImported() {
        return (customerRepository.count() > 0);
    }
}
