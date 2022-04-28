package no.ntnu.bicycle.tools;

import no.ntnu.bicycle.mail.EmailSenderService;
import no.ntnu.bicycle.model.*;
import no.ntnu.bicycle.repository.CustomerRepository;
import no.ntnu.bicycle.repository.OrderRepository;
import no.ntnu.bicycle.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

@Component
public class DummyDataInitializer implements ApplicationListener<ApplicationReadyEvent>{

    private CustomerRepository customerRepository;


    private OrderRepository orderRepository;


    private ProductRepository productRepository;

    @Autowired
    private EmailSenderService emailSenderService;


    public DummyDataInitializer(CustomerRepository customerRepository, OrderRepository orderRepository, ProductRepository productRepository) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    private final Logger logger = Logger.getLogger("DummyInit");

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (isDataImported()){
            logger.info("Data already exists");
            return;
        }

        logger.info("Importing test data...");
        Customer sebastian = new Customer("Sebastian", "Nilsen", "sebasn@stud.ntnu.no","2001-04-19",94198166,"$2a$12$QjPXqckLsFqDDRxrEfboC.0WYcUSP5wMhuOftGkcnpA9vI1sUOiWa", Role.ROLE_ADMIN);
        Customer anne = new Customer("Anne", "Ruud", "anneruud@mail.no","1994-06-04",94198782,"$2a$12$QjPXqckLsFqDDRxrEfboC.0WYcUSP5wMhuOftGkcnpA9vI1sUOiWa",Role.ROLE_USER);
        //Customer fereshta = new Customer("Fereshta", "Ahmadi", "fereshta@stud.ntnu.no", "2000-06-11", 929394949, "$2a$12$QjPXqckLsFqDDRxrEfboC.0WYcUSP5wMhuOftGkcnpA9vI1sUOiWa", Role.ROLE_USER);

        BillingAndShippingAddress adresse1 = new BillingAndShippingAddress("Sebastian","Nilsen","Fiolveien 1b","Norway","1395","Hvalstad");

        sebastian.setAddress(adresse1);

        customerRepository.saveAll(List.of(sebastian,anne));

        Product blueHelmet = new Product("Blue helmet","blue-helmet.png",199);
        Product whiteHelmet = new Product("White helmet","white-helmet.png",199);
        Product blueHelmet1 = new Product("Blue helmet","blue-helmet.png",199);
        Product whiteHelmet1 = new Product("White helmet","white-helmet.png",199);
        Product blueHelmet2 = new Product("Blue helmet","blue-helmet.png",199);
        Product whiteHelmet2 = new Product("White helmet","white-helmet.png",199);

        productRepository.saveAll(List.of(blueHelmet,whiteHelmet,blueHelmet1,whiteHelmet1,blueHelmet2,whiteHelmet2));

        CustomerOrder order1 = new CustomerOrder(sebastian,blueHelmet);
        CustomerOrder order2 = new CustomerOrder(anne,whiteHelmet);

        orderRepository.saveAll(List.of(order1,order2));

        //emailSenderService.sendEmail("sebasn@stud.ntnu.no","test of mail service class", "This is a test for mail service class. below is a test for html <b> halla </b>");


    }

    private boolean isDataImported() {
        return (customerRepository.count() > 0);
    }
}
