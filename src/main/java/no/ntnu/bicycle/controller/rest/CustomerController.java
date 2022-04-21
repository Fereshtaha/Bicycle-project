package no.ntnu.bicycle.controller.rest;

import no.ntnu.bicycle.mail.EmailSenderService;
import no.ntnu.bicycle.model.Customer;
import no.ntnu.bicycle.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    CustomerService customerService;

    EmailSenderService emailSenderService;

    public CustomerController(CustomerService customerService, EmailSenderService emailSenderService) {
        this.customerService = customerService;
        this.emailSenderService = emailSenderService;
    }

    @GetMapping
    public List<Customer> getCustomer() {
        return customerService.getAllCustomers();
    }

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

    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> registerNewCustomer(@RequestBody Customer customer) {
        ResponseEntity<String> response;
        if (customerService.addNewCustomer(customer)) {
            response = new ResponseEntity<>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @PostMapping(value = "/reset-password", consumes = "application/json")
    public ResponseEntity<String> resetPassword(@RequestBody String emailObject) {
        String[] stringArray = emailObject.split("\"" );
        String email = stringArray[3];
        ResponseEntity<String> response = null;
        Customer customer = customerService.findCustomerByEmail(email);
        if (customer != null) {
            String generatedPassword = customerService.resetPassword(email);
            if (generatedPassword != null){
                try{
                    emailSenderService.sendEmail(email, "Password reset", "You're new password is: " + generatedPassword);
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
        return response;
    }

    @PostMapping(value = "/update-password", consumes = "application/json")
    public ResponseEntity<String> updatePassword(@RequestBody String emailAndNewAndOldPassword){
        ResponseEntity<String> response;

        String[] stringArray = emailAndNewAndOldPassword.split("\"" );

        String email = stringArray[3];
        String oldPassword = stringArray[7];
        String newPassword = stringArray[11];
        Customer customer = customerService.findCustomerByEmail(email);
        if (customer.isValid()){
             if(new BCryptPasswordEncoder().matches(oldPassword, customer.getPassword())){
                customer.setPassword(new BCryptPasswordEncoder().encode(newPassword));
                response = new ResponseEntity<>(HttpStatus.OK);
                customerService.updateCustomer(customer.getId(),customer);
            }else{
                 response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
             }
        }else{
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
    }



    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable("id") int customerId) {
        customerService.deleteCustomer(customerId);
    }

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
}
