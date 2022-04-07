package no.ntnu.bicycle.service;

import no.ntnu.bicycle.model.Payment;
import no.ntnu.bicycle.repository.PaymentsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentsService {

    private PaymentsRepository paymentsRepository;

    public PaymentsService(PaymentsRepository paymentsRepository) {
        this.paymentsRepository = paymentsRepository;
    }

    public List<Payment> getAllPayments() {
        return paymentsRepository.findAll();
    }

    public Payment findPaymentsById(int id) {
        Optional<Payment> payments = paymentsRepository.findById(id);
        return payments.orElse(null);
    }

    public boolean addNewPayment(Payment payment) {
        boolean added = false;
        if (payment != null) { // && payments.isValid()
            Payment existingPayment = findPaymentsById(payment.getCheckNumber());
            if (existingPayment == null) {
                paymentsRepository.save(payment);
                added = true;
            }
        }
        return added;
    }

    public boolean deletePayment(int paymentsId) {
        Optional<Payment> payment = paymentsRepository.findById(paymentsId);
        if (payment.isPresent()) {
            paymentsRepository.delete(payment.get());
        }
        return payment.isPresent();
    }


    public String updatePayments(int paymentsId, Payment payment) {
        String errorMessage = null;
        Payment existingsPayment = findPaymentsById(paymentsId);
        if (existingsPayment == null) {
            errorMessage = "No payment with id" + paymentsId + "exists";

        } else if (payment == null) {
            errorMessage = "Invalid payment data";
        } else if (payment.getCustomerNumber() != paymentsId) {
            errorMessage = "Id does not match customer number";
        }

        if (errorMessage == null) {
            paymentsRepository.save(payment);
        }
        return errorMessage;
    }




}
