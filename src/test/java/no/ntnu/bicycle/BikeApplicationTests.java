package no.ntnu.bicycle;
import static org.junit.jupiter.api.Assertions.assertEquals;


import no.ntnu.bicycle.model.Customer;
import no.ntnu.bicycle.model.Role;
import no.ntnu.bicycle.repository.CustomerRepository;
import no.ntnu.bicycle.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BikeApplicationTests {

	//@Test
	//void test_findCustomerByEmail() {
	//	Customer sebastian = new Customer("Sebastian", "Nilsen", "sebasn@stud.ntnu.no","2001-04-19",94658622,"$2a$12$QjPXqckLsFqDDRxrEfboC.0WYcUSP5wMhuOftGkcnpA9vI1sUOiWa", Role.ROLE_ADMIN);
	//	assertEquals("sebasn@stud.ntnu.no", sebastian.getEmail());
	//}


	//@Test
	//void contextLoads() {
	//}

}
