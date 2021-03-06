package no.ntnu.bicycle.controller.rest;

import no.ntnu.bicycle.mail.EmailSenderService;
import no.ntnu.bicycle.model.BillingAndShippingAddress;
import no.ntnu.bicycle.model.Customer;
import no.ntnu.bicycle.model.CustomerOrder;
import no.ntnu.bicycle.model.Email;
import no.ntnu.bicycle.service.CustomerService;
import no.ntnu.bicycle.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.websocket.server.PathParam;
import java.util.*;

/**
 * REST API controller for customer.
 */
@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    private final EmailSenderService emailSenderService;

    private final ProductService productService;

    /**
     * Constructor with parameters
     * @param customerService customer service
     * @param emailSenderService email sender service
     */
    public CustomerController(CustomerService customerService, EmailSenderService emailSenderService, ProductService productService) {
        this.customerService = customerService;
        this.emailSenderService = emailSenderService;
        this.productService = productService;
    }

    /**
     * Gets all customers
     * HTTP get
     * @return list of all customers
     */
    @GetMapping
    public List<Customer> getCustomer() {
        return customerService.getAllCustomers();
    }

    /**
     * Gets one specific customer
     * @param customerId ID of the customer to be returned
     * @return Customer with the given ID or status 404
     */
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getOneCustomer(@PathParam("costumer") @PathVariable("id") int customerId){
        ResponseEntity<Customer> response;
        Customer customer = customerService.findCustomerById(customerId);
        if (customer != null) {
            response = new ResponseEntity<>(customer, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    /**
     * Gets the customer that is logged in
     * @return Customer by email, 404 not found or 403 forbidden.
     */
    @GetMapping("/authenticated-customer")
    public ResponseEntity<Customer> getOneCustomerByEmail(){
        ResponseEntity<Customer> response;
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String customerEmail = auth.getName();
            Customer customer = customerService.findCustomerByEmail(customerEmail);

            if (customer != null) {
                response = new ResponseEntity<>(customer, HttpStatus.OK);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (NoSuchElementException e){
            response = new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return response;
    }

    /**
     * Gets address of customer by email
     * @return Address of customer by email, 404 not found or 403 forbidden.
     */
    @GetMapping("/authenticated-address")
    public ResponseEntity<BillingAndShippingAddress> getAddressOfCustomerByEmail(){
        ResponseEntity<BillingAndShippingAddress> response;
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String customerEmail = auth.getName();
            Customer customer = customerService.findCustomerByEmail(customerEmail);

            if (customer != null) {
                response = new ResponseEntity<>(customer.getAddress(), HttpStatus.OK);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (NoSuchElementException e){
            response = new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return response;
    }

    /**
     * Updates address of customer
     * @param address address to be updated
     * @return 200 OK status on success, 404 not found or 403 forbidden.
     */
    @PostMapping("/authenticated-address")
    public ResponseEntity<String> updateAddressOfCustomer(@RequestBody BillingAndShippingAddress address) {
        ResponseEntity<String> response;
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String customerEmail = auth.getName();
            Customer customer = customerService.findCustomerByEmail(customerEmail);

            if (customer != null) {
                customer.setAddress(address);
                customerService.updateCustomer(customer.getId(), customer);
                response = new ResponseEntity<>(HttpStatus.OK);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (NoSuchElementException e){
            response = new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return response;
    }

    /**
     * Registers new customer
     * @param customer customer to be registered
     * @return 200 OK status on success = welcome mail from KRRR,
     * or 400 bad request and prints out that mail could not be sent
     */
    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> registerNewCustomer(@RequestBody Customer customer)  {
        ResponseEntity<String> response;
        if (customerService.addNewCustomer(customer)) {
            response = new ResponseEntity<>(HttpStatus.OK);
            try {
                Email email = new Email();
                email.setTo(customer.getEmail());
                email.setSubject("Welcome Email from Keep rolling, rolling, rolling");
                email.setTemplate("welcome-email.html");
                Map<String, Object> properties = new HashMap<>();
                properties.put("name", customer.getFirstName());
                email.setProperties(properties);

                emailSenderService.sendHtmlMessage(email);
            }catch (MessagingException e){
                System.err.println("Welcome mail could not be sent");
            }
        } else {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    /**
     * Reset password
     * @param emailObject email to the password that needs to be reset
     * @return 200 OK status on success = mail with new password to the email given,
     * 400 bad request or 404 not found
     */
    @PostMapping(value = "/reset-password", consumes = "application/json")
    public ResponseEntity<String> resetPassword(@RequestBody String emailObject) {
        String[] stringArray = emailObject.split("\"" );
        String email = stringArray[3];
        ResponseEntity<String> response = null;
        try {
            Customer customer = customerService.findCustomerByEmail(email);
        if (customer != null) {
            String generatedPassword = customerService.resetPassword(email);
            if (generatedPassword != null){
                try{
                    emailSenderService.sendEmail(email, "Password reset", "Your new password is: " + generatedPassword);
                    response = new ResponseEntity<>(HttpStatus.OK);
                }catch (MailException e){
                    e.printStackTrace();
                    response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            System.out.println("Customer doesnt exist");
        }
        }catch (NoSuchElementException e){
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    /**
     * !TODO documentation here
     * Updates password
     * @param emailAndNewAndOldPassword Email, new and old password
     * @return 200 OK status on success =
     * 401 Unauthorized =
     * 404 not found =
     */
    @PostMapping(value = "/update-password", consumes = "application/json")
    public ResponseEntity<String> updatePassword(@RequestBody String emailAndNewAndOldPassword){
        ResponseEntity<String> response;

        String[] stringArray = emailAndNewAndOldPassword.split("\"" );

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        String oldPassword = stringArray[3];
        String newPassword = stringArray[7];
        Customer customer = customerService.findCustomerByEmail(email);
        if (customer.isValid()){
             if(new BCryptPasswordEncoder().matches(oldPassword, customer.getPassword())){
                customer.setPassword(new BCryptPasswordEncoder().encode(newPassword));
                customerService.updateCustomer(customer.getId(),customer);
                 response = new ResponseEntity<>(HttpStatus.OK);
            }else{
                 response = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
             }
        }else{
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }


    /**
     * Deletes a customer
     * @param customerId customer to be deleted
     */
    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable("id") int customerId) {
        customerService.deleteCustomer(customerId);
    }

    /**
     * Update customer
     * @param id id of the customer that needs to be updated
     * @param customer customer that needs to be updated
     * @return 200 OK status on success or 400 bad request if it does not get updated
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable int id,
                                         @RequestBody Customer customer) {
        String errorMessage = customerService.updateCustomer(id, customer);
        ResponseEntity<String> response;
        if (errorMessage == null) {
            response = new ResponseEntity<>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    /**
     * Deletes a product in the cart
     * @param id the id of the product
     * @return
     */
    @DeleteMapping(value = "/deleteProductInCart")
    public ResponseEntity<String> deleteProductInCart(@RequestBody int id){
        ResponseEntity<String> response;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        try{
            Customer customer = customerService.findCustomerByEmail(email);

            customer.removeFromShoppingCart(productService.findOrderById(id));

            customerService.updateCustomer(customer.getId(), customer);

            response = new ResponseEntity<>(HttpStatus.OK);
        }catch (NoSuchElementException e){
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return response;
    }
}
