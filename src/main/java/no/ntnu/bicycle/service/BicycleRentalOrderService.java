package no.ntnu.bicycle.service;

import no.ntnu.bicycle.model.BicycleRentalOrder;
import no.ntnu.bicycle.repository.BicycleRentalOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Business logic related to Bicycle rental orders
 */
@Service
public class BicycleRentalOrderService {
    @Autowired
    BicycleRentalOrderRepository bicycleRentalOrderRepository;

    /**
     * Constructor for bicycle rental order
     * @param bicycleRentalOrderRepository BicycleRentalOrderRepository
     */
    public BicycleRentalOrderService(BicycleRentalOrderRepository bicycleRentalOrderRepository){
        this.bicycleRentalOrderRepository = bicycleRentalOrderRepository;
    }

    /**
     * Finds bicycle rental order by id
     * @param id long. Bicycle id
     * @return The bicycle rental
     * @throws NoSuchElementException
     */
    public BicycleRentalOrder findBicycleRentalOrderById(long id) throws NoSuchElementException {
        Optional<BicycleRentalOrder> bicycleRentalOrder = bicycleRentalOrderRepository.findById(id);

        return bicycleRentalOrder.get();
    }

    /**
     * Adds bicycle rental order
     * @param bicycleRentalOrder BicycleRentalOrder
     * @return true when added, false on error
     */
    public boolean addBicycleRentalOrder(BicycleRentalOrder bicycleRentalOrder){
        try{
            findBicycleRentalOrderById(bicycleRentalOrder.getId());
            return false;
        }catch (NoSuchElementException e){
            bicycleRentalOrderRepository.save(bicycleRentalOrder);
            return true;
        }
    }
}
