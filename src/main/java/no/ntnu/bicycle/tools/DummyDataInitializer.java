package no.ntnu.bicycle.tools;

import lombok.SneakyThrows;
import no.ntnu.bicycle.mail.EmailSenderService;
import no.ntnu.bicycle.model.*;
import no.ntnu.bicycle.repository.BicycleRepository;
import no.ntnu.bicycle.repository.CustomerRepository;
import no.ntnu.bicycle.repository.OrderRepository;
import no.ntnu.bicycle.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

/**
 * This class initializes example data
 */
@Component
public class DummyDataInitializer implements ApplicationListener<ApplicationReadyEvent>{

    private CustomerRepository customerRepository;


    private OrderRepository orderRepository;


    private ProductRepository productRepository;

    private BicycleRepository bicycleRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    /**
     * Constructor for dummy data initializer
     * @param customerRepository CustomerRepository
     * @param orderRepository OrderRepository
     * @param productRepository ProductRepository
     * @param bicycleRepository BicycleRepository
     */
    public DummyDataInitializer(CustomerRepository customerRepository, OrderRepository orderRepository, ProductRepository productRepository, BicycleRepository bicycleRepository) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.bicycleRepository = bicycleRepository;
    }

    private final Logger logger = Logger.getLogger("DummyInit");

    @SneakyThrows
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (isDataImported()){
            logger.info("Data already exists");
            return;
        }

        logger.info("Importing test data...");
        Customer sebastian = new Customer("Ola", "Halvorsen", "admin@sensor.no","2001-04-19",94198176,"$2a$12$QjPXqckLsFqDDRxrEfboC.0WYcUSP5wMhuOftGkcnpA9vI1sUOiWa", Role.ROLE_ADMIN);
        Customer anne = new Customer("Anne", "Ruud", "user@sensor.no","1994-06-04",94198782,"$2a$12$QjPXqckLsFqDDRxrEfboC.0WYcUSP5wMhuOftGkcnpA9vI1sUOiWa",Role.ROLE_USER);

        BillingAndShippingAddress adresse1 = new BillingAndShippingAddress("Ola","Halvorsen","Vaagavegen 39","Norway","6005","Oslo");

        sebastian.setAddress(adresse1);

        customerRepository.saveAll(List.of(sebastian,anne));

        Product blueHelmet = new Product("Blue helmet","blue","blue-helmet.png","Save my Brain er en rimelig hjelm som ogs?? har godkjenning CE 1078. Hjelmen tilpasses enkelt til justeringsskruen i nakken og passer dermed til flere forskjellige barn eller for barnets utvikling.",199);
        Product whiteHelmet = new Product("White helmet","white","white-helmet.png","Save my Brain er en rimelig hjelm som ogs?? har godkjenning CE 1078. Hjelmen tilpasses enkelt til justeringsskruen i nakken og passer dermed til flere forskjellige barn eller for barnets utvikling.",199);
        Product blueHelmet1 = new Product("Blue bag","blue","blue-bag.png","Eco-cotton, Fairtrade and Impress your friends. Buy today and get sunglasses for free. Pick up when renting a bike",250);
        Product whiteHelmet1 = new Product("White bag","white","white-bag.png","Eco-cotton, Fairtrade and Impress your friends. Buy today and get sunglasses for free. Pick up when renting a bike",250);



        productRepository.saveAll(List.of(blueHelmet,whiteHelmet,blueHelmet1,whiteHelmet1));


        CustomerOrder order1 = new CustomerOrder(sebastian,List.of(blueHelmet,whiteHelmet));
        CustomerOrder order2 = new CustomerOrder(anne,List.of(blueHelmet1));

        orderRepository.saveAll(List.of(order1,order2));

        Bicycle bicycle = new Bicycle("Brown", "62.47433372997846, 6.164537350482638", 1, "NEW");
        bicycle.setStatusToAvailable();
        Bicycle bicycle1 = new Bicycle("Green", "62.47309499833774, 6.153352263095387", 1, "NEW");
        bicycle1.setStatusToAvailable();

        bicycleRepository.saveAll(List.of(bicycle, bicycle1));
    }

    /**
     * Checking if data is imported
     * @return true if data is imported, false if it's not imported
     */
    private boolean isDataImported() {
        return (customerRepository.count() > 0);
    }
}
