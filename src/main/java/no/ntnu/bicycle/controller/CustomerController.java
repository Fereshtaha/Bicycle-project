package no.ntnu.bicycle.controller;

import no.ntnu.bicycle.model.Customer;
import no.ntnu.bicycle.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("customers")
public class CustomerController {
    @Autowired
    CustomerService customerService;

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

    @PostMapping
    public ResponseEntity<String> registerNewCustomer(@RequestBody Customer customer) {
        ResponseEntity<String> response;
        if (customerService.addNewCustomer(customer)) {
            response = new ResponseEntity<>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
