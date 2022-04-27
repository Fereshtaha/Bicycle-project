package no.ntnu.bicycle.controller.rest;

import no.ntnu.bicycle.mail.EmailSenderService;
import no.ntnu.bicycle.model.BillingAndShippingAddress;
import no.ntnu.bicycle.model.Customer;
import no.ntnu.bicycle.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.NoSuchElementException;
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
                 System.out.println("Password updated");
            }else{
                 response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                 System.err.println("Error: Password not updated, password doesnt match");
             }
        }else{
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            System.err.println("Error: Password not updated, user not found");
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
