package no.ntnu.bicycle.tools;

import no.ntnu.bicycle.model.Customer;
import no.ntnu.bicycle.model.CustomerOrder;
import no.ntnu.bicycle.model.Product;
import no.ntnu.bicycle.repository.CustomerRepository;
import no.ntnu.bicycle.repository.OrderRepository;
import no.ntnu.bicycle.repository.ProductRepository;
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

    private final Logger logger = Logger.getLogger("DummyInit");

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (isDataImported()){
            logger.info("Data already exists");
            return;
        }
        logger.info("Importing data...");
        Customer sebbern = new Customer("Sebastian Nilsen","snilse@mail.no", LocalDate.of(2001, Month.APRIL,19));
        Customer anne = new Customer("Anne Ruud","anne.ruud@mail.no", LocalDate.of(1994, Month.SEPTEMBER,05));

        customerRepository.saveAll(List.of(sebbern,anne));

        Product blue_helmet = new Product("Blue helmet",199);
        Product white_helmet = new Product("White helmet",199);

        productRepository.saveAll(List.of(blue_helmet,white_helmet));

        CustomerOrder order1 = new CustomerOrder(sebbern,blue_helmet);
        CustomerOrder order2 = new CustomerOrder(anne,white_helmet);

        orderRepository.saveAll(List.of(order1,order2));



    }

    private boolean isDataImported() {
        return (customerRepository.count() > 0);
    }
}
