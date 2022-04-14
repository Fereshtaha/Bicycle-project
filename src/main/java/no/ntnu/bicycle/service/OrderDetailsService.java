package no.ntnu.bicycle.service;

import no.ntnu.bicycle.model.OrderDetails;
import no.ntnu.bicycle.model.Payment;
import no.ntnu.bicycle.repository.OrderDetailsRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailsService {
    private OrderDetailsRepository orderDetailsRepository;

    public List<OrderDetails> getAllOrderDetails() {
        return orderDetailsRepository.findAll();
    }

    public OrderDetails findPaymentByOrderNumber(int orderNumber){
        Optional<OrderDetails> payment = orderDetailsRepository.findById(orderNumber);
        return payment.orElse(null);
    }

    public boolean addOrderDetails(OrderDetails orderDetails) {
        boolean added = false;
        if (orderDetails != null) {
            OrderDetails existingOrder = findPaymentByOrderNumber(orderDetails.getOrderNumber());
            if (existingOrder == null) {
                orderDetailsRepository.save(orderDetails);
                added = true;
            }
        }
        return added;
    }

    public boolean deleteOrderDetails(int orderNumber) {
        Optional<OrderDetails> orderDetails = orderDetailsRepository.findById(orderNumber);
        if (orderDetails.isPresent()) {
            orderDetailsRepository.delete(orderDetails.get());
        }
        return orderDetails.isPresent();
    }

    @Transactional
    public String updateOrderDetails(int orderNumber, OrderDetails orderDetails) {
        String errorMessage = null;
        OrderDetails existingOrderDetails = findPaymentByOrderNumber(orderNumber);
        if (existingOrderDetails == null) {
            errorMessage = "No order detail with this ordernumber: " + orderNumber;
        } else if (orderDetails == null) {
            errorMessage = "Invalid ordernumber";
        } else if (orderDetails.getOrderNumber() != orderNumber) {
            errorMessage = "Ordernumber does not match";
        }
        if (errorMessage == null) {
            orderDetailsRepository.save(orderDetails);
        }
        return errorMessage;
    }
}
