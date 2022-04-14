package no.ntnu.bicycle.service;

import no.ntnu.bicycle.model.Payment;
import no.ntnu.bicycle.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    private PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment findPaymentsById(int number) {
        Optional<Payment> payments = paymentRepository.findById(number);
        return payments.orElse(null);
    }

    public boolean addNewPayment(Payment payment) {
        boolean added = false;
        if (payment != null) { // && payments.isValid()
            Payment existingPayment = findPaymentsById(payment.getCheckNumber());
            if (existingPayment == null) {
                paymentRepository.save(payment);
                added = true;
            }
        }
        return added;
    }

    public boolean deletePayment(int paymentNumber) {
        Optional<Payment> payment = paymentRepository.findById(paymentNumber);
        if (payment.isPresent()) {
            paymentRepository.delete(payment.get());
        }
        return payment.isPresent();
    }


    public String updatePayments(int paymentNumber, Payment payment) {
        String errorMessage = null;
        Payment existingsPayment = findPaymentsById(paymentNumber);
        if (existingsPayment == null) {
            errorMessage = "No payment with id" + paymentNumber + "exists";

        } else if (payment == null) {
            errorMessage = "Invalid payment data";
        } else if (payment.getCustomerNumber() != paymentNumber) {
            errorMessage = "Id does not match customer number";
        }

        if (errorMessage == null) {
            paymentRepository.save(payment);
        }
        return errorMessage;
    }




}
