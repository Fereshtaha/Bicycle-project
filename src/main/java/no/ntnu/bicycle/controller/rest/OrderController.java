package no.ntnu.bicycle.controller.rest;

import com.fasterxml.jackson.databind.JsonNode;
import no.ntnu.bicycle.model.Bicycle;
import no.ntnu.bicycle.model.BicycleRentalOrder;
import no.ntnu.bicycle.model.Customer;
import no.ntnu.bicycle.model.CustomerOrder;
import no.ntnu.bicycle.service.BicycleRentalOrderService;
import no.ntnu.bicycle.service.BicycleService;
import no.ntnu.bicycle.service.CustomerService;
import no.ntnu.bicycle.service.OrderService;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private OrderService orderService;
    private BicycleRentalOrderService bicycleRentalOrderService;
    private CustomerService customerService;
    private BicycleService bicycleService;

    public OrderController(OrderService orderService, BicycleRentalOrderService bicycleRentalOrderService, CustomerService customerService, BicycleService bicycleService) {
        this.orderService = orderService;
        this.bicycleRentalOrderService = bicycleRentalOrderService;
        this.customerService = customerService;
        this.bicycleService = bicycleService;
    }

    @GetMapping
    public List<CustomerOrder> getAllOrders(@RequestParam(required = false) String customer,
                                            @RequestParam(required = false) String product) {
        if (product != null && !"".equals(product)) {
            if (customer != null && !"".equals(customer)) {
                return orderService.getAllOrdersByCustomerAndProduct(customer, product);
            } else {
                return orderService.getAllOrdersByCustomer(customer);
            }
        } else if(customer != null && !"".equals(customer)) {
            return orderService.getAllOrdersByProducts(product);
        } else {
            return orderService.getAll();
        }
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> add(@RequestBody CustomerOrder customerOrder) {
        ResponseEntity<String> response;
        if (orderService.addNewOrder(customerOrder)) {
            response = new ResponseEntity<>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerOrder> getOne(@PathVariable Integer id) {
        ResponseEntity<CustomerOrder> response;
        CustomerOrder customerOrder = orderService.findOrderById(id);
        if (customerOrder != null) {
            response = new ResponseEntity<>(customerOrder, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        ResponseEntity<String> response;
        if (orderService.deletingOrder(id)) {
            response = new ResponseEntity<>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }


    @PutMapping
    public ResponseEntity<String> update(@PathVariable int id, @RequestBody CustomerOrder customerOrder) {
        String errorMessage = orderService.update(id, customerOrder);
        ResponseEntity<String> response;
        if (errorMessage == null) {
            response = new ResponseEntity<>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
        return  response;
    }

    @PostMapping(value = "/rental", consumes = "application/json")
    public ResponseEntity<String> createBikeRentalOrder(HttpEntity<String> entity){

        //JSONObject json = new JSONObject(entity.getBody());

        JSONObject jsonObject = new JSONObject(entity.getBody());

        long id = Long.parseLong(jsonObject.getString("bikeId"));
        String location = jsonObject.getString("location");
        int pricePerMinute = Integer.parseInt(jsonObject.getString("pricePerMinute"));

        ResponseEntity<String> response;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        try {
            Customer customer = customerService.findCustomerByEmail(email);
            Bicycle bicycle = bicycleService.findBicycleById(id);

            bicycleRentalOrderService.addBicycleRentalOrder(new BicycleRentalOrder(bicycle, customer, location, pricePerMinute));

            response = new ResponseEntity<>(HttpStatus.OK);
        }catch (NoSuchElementException e){
            e.printStackTrace();
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return response;
    }

}
