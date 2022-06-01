package no.ntnu.bicycle.service;

import no.ntnu.bicycle.model.CustomerOrder;
import no.ntnu.bicycle.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * Business logic related to orders
 */
@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    /**
     * Constructor for order repository
     * @param orderRepository
     */
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Makes iterable to list
     * @param iterable Iterable<CustomerOrder>
     * @return list of customer orders
     */
    public List<CustomerOrder> iterableToList(Iterable<CustomerOrder> iterable) {
        List<CustomerOrder> list = new LinkedList<>();
        iterable.forEach(list::add);
        return list;
    }

    /**
     * Gets all customer orders
     * @return list of customer order
     */
    public List<CustomerOrder> getAll() {
        return iterableToList(orderRepository.findAll());
    }

    /**
     * Finds the order by id
     * @param id Integer
     * @return The order or null if none found by that ID
     */
    public CustomerOrder findOrderById(Integer id) {
        return orderRepository.findById(id).orElse(null);
    }

    /**
     * Adds new customer order
     * @param customerOrder CustomerOrder
     * @return true when order added, false on error
     */
    public boolean addNewOrder(CustomerOrder customerOrder) {
        boolean added = false;
        if (canBeAdded(customerOrder)) {
            orderRepository.save(customerOrder);
            added = true;
        }
        return added;
    }

    //!TODO fix this
    private boolean canBeAdded(CustomerOrder customerOrder) {
        return customerOrder != null;

    }

    /**
     * Deletes an order
     * @param orderId int
     * @return true when order is deleted, false when order was not found in the database
     */
    public boolean deletingOrder(int orderId) {
    boolean deleted = false;
    if (findOrderById(orderId) != null) {
        orderRepository.deleteById(orderId);
        deleted = true;
        }
    return deleted;
    }

    /**
     * Updates an order
     * @param id int
     * @param customerOrder CustomerOrder
     * @return null on success, error message on error
     */
    public String update(int id, CustomerOrder customerOrder) {
        CustomerOrder existingCustomerOrder = findOrderById(id);
        String errorMessage = null;
        if (existingCustomerOrder == null) {
            errorMessage = "No customerOrder with " + id + "found";
        }
        if (customerOrder == null) {
            errorMessage = "Wrong data in request body";
        } else if (customerOrder.getId() != id) {
            errorMessage = "Wrong id, does not match";
        }

        if (errorMessage == null) {
            orderRepository.save(customerOrder);
        }
        return errorMessage;
    }
//!TODO what do we do about these?
    public List<CustomerOrder> getAllOrdersByCustomer(String customer) {
        //return orderRepository.findByCustomerName(customer);
        return null;
    }

    public List<CustomerOrder> getAllOrdersByProducts(String product) {
        return null;
        //return orderRepository.findByProductName(product);
    }


    public List<CustomerOrder> getAllOrdersByCustomerAndProduct(String customer, String product) {
      //  return orderRepository.findByCustomerNameAndProduct(customer,product);
        return null;
    }

}
