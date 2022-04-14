package no.ntnu.bicycle.controller;

import no.ntnu.bicycle.model.CustomerOrder;
import no.ntnu.bicycle.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
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

    @PostMapping
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

    @DeleteMapping
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

}
