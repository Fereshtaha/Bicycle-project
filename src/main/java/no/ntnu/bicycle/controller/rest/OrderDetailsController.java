package no.ntnu.bicycle.controller.rest;

import no.ntnu.bicycle.model.OrderDetails;
import no.ntnu.bicycle.service.OrderDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

public class OrderDetailsController {
    OrderDetailsService orderDetailsService;

    public OrderDetailsController(OrderDetailsService orderDetailsService) {
        this.orderDetailsService = orderDetailsService;
    }

    @GetMapping
    public List<OrderDetails> getOrderDetails() {
        return orderDetailsService.getAllOrderDetails();
    }


    @GetMapping("/{id}")
    public ResponseEntity<OrderDetails> getOrderDetails(@PathParam("orderDetails")
                                                        @PathVariable("id")
                                                        int orderNumber) {
        ResponseEntity<OrderDetails> response;
        OrderDetails orderDetails = orderDetailsService.findPaymentByOrderNumber(orderNumber);
        if (orderDetails != null) {
            response = new ResponseEntity<>(orderDetails, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @PostMapping
    public ResponseEntity<String> registerNewOrderDetail(@RequestBody OrderDetails orderDetails) {
        ResponseEntity<String> response;
        if (orderDetailsService.addOrderDetails(orderDetails)) {
            response = new ResponseEntity<>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public void deleteOrderDetail(@PathVariable("id") int orderNumber) {
        orderDetailsService.deleteOrderDetails(orderNumber);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateOrderDetail(@PathVariable int orderNumber,
                                                    @RequestBody OrderDetails orderDetails) {
        String errorMessage = orderDetailsService.updateOrderDetails(orderNumber, orderDetails);
        ResponseEntity<String> response;
        if (errorMessage == null) {
            response = new ResponseEntity<>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
        return response;
    }

}
