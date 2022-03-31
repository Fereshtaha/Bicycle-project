package no.ntnu.bicycle.order;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class OrderService {

    private OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<CustomerOrder> iterableToList(Iterable<CustomerOrder> iterable) {
        List<CustomerOrder> list = new LinkedList<>();
        iterable.forEach(list::add);
        return list;
    }

    public List<CustomerOrder> getAll() {
        return iterableToList(orderRepository.findAll());
    }

    public CustomerOrder findOrderById(Integer id) {
        return orderRepository.findById(id).orElse(null);
    }

    public boolean addNewOrder(CustomerOrder customerOrder) {
        boolean added = false;
        if (canBeAdded(customerOrder)) {
            orderRepository.save(customerOrder);
            added = true;
        }
        return added;
    }

    //Fixing this later
    private boolean canBeAdded(CustomerOrder customerOrder) {
        return customerOrder != null;

        }

        public boolean deletingOrder(int orderId) {
        boolean deleted = false;
        if (findOrderById(orderId) != null) {
            orderRepository.deleteById(orderId);
            deleted = true;
        }
        return deleted;
    }


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
