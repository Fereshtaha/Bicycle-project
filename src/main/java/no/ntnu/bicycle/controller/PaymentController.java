package no.ntnu.bicycle.controller;

import no.ntnu.bicycle.model.Payment;
import no.ntnu.bicycle.service.PaymentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("payment")
public class PaymentController {
    @Autowired
    PaymentsService paymentsService;

    @GetMapping
    public List<Payment> getPayment() {return paymentsService.getAllPayments();}

    @GetMapping("{id}")
    public ResponseEntity<Payment> getOnePayment(@PathParam("payment")
                                                 @PathVariable("id")
                                                 int paymentId){
        ResponseEntity<Payment> response;
        Payment payment = paymentsService.findPaymentsById(paymentId);
        if (payment != null) {
            response = new ResponseEntity<>(payment, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @PostMapping
    public ResponseEntity<String> registerNewPayment(@RequestBody Payment payment) {
        ResponseEntity<String> response;
        if (paymentsService.addNewPayment(payment)) {
            response = new ResponseEntity<>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public void deletePayment(@PathVariable("id")
                              int paymentId) {
        paymentsService.deletePayment(paymentId);
    }

    @PutMapping("/{id}")

    public ResponseEntity<String> update(@PathVariable int id,
                                         @RequestBody Payment payment) {
        String errorMessage = paymentsService.updatePayments(id, payment);
        ResponseEntity<String> response;
        if (errorMessage == null) {
            response = new ResponseEntity<>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
        return response;
    }



}
