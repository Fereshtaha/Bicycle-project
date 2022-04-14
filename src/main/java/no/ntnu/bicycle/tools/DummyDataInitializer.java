package no.ntnu.bicycle.tools;

import no.ntnu.bicycle.mail.EmailSenderService;
import no.ntnu.bicycle.model.Customer;
import no.ntnu.bicycle.model.CustomerOrder;
import no.ntnu.bicycle.model.Product;
import no.ntnu.bicycle.repository.CustomerRepository;
import no.ntnu.bicycle.repository.OrderRepository;
import no.ntnu.bicycle.repository.ProductRepository;
import no.ntnu.bicycle.service.CustomerService;
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
    private EmailSenderService emailSenderService;


    private final Logger logger = Logger.getLogger("DummyInit");

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (isDataImported()){
            logger.info("Data already exists");
            return;
        }
        logger.info("Importing data...");
        Customer sebastian = new Customer("Sebastian", "Nilsen", "sebasn@stud.ntnu.no","2001-04-19",94658622,"$2a$12$QjPXqckLsFqDDRxrEfboC.0WYcUSP5wMhuOftGkcnpA9vI1sUOiWa","ROLE_ADMIN");
        Customer anne = new Customer("Anne", "Ruud", "anne.ruud@mail.no","1994-06-04",94198782,"$2a$12$QjPXqckLsFqDDRxrEfboC.0WYcUSP5wMhuOftGkcnpA9vI1sUOiWa","ROLE_USER");

        customerRepository.saveAll(List.of(sebastian,anne));

        Product blueHelmet = new Product("Blue helmet",199);
        Product whiteHelmet = new Product("White helmet",199);

        productRepository.saveAll(List.of(blueHelmet,whiteHelmet));

        CustomerOrder order1 = new CustomerOrder(sebastian,blueHelmet);
        CustomerOrder order2 = new CustomerOrder(anne,whiteHelmet);

        orderRepository.saveAll(List.of(order1,order2));

        //emailSenderService.sendEmail("sebasn@stud.ntnu.no","test of mail service class", "This is a test for mail service class. below is a test for html <b> halla </b>");


    }

    private boolean isDataImported() {
        return (customerRepository.count() > 0);
    }
}
