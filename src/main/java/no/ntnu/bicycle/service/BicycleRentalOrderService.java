package no.ntnu.bicycle.service;

import no.ntnu.bicycle.model.BicycleRentalOrder;
import no.ntnu.bicycle.repository.BicycleRentalOrderRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class BicycleRentalOrderService {
    BicycleRentalOrderRepository bicycleRentalOrderRepository;

    public BicycleRentalOrderService(BicycleRentalOrderRepository bicycleRentalOrderRepository){
        this.bicycleRentalOrderRepository = bicycleRentalOrderRepository;
    }

    public BicycleRentalOrder findBicycleRentalOrderById(long id) throws NoSuchElementException {
        Optional<BicycleRentalOrder> bicycleRentalOrder = bicycleRentalOrderRepository.findById(id);

        return bicycleRentalOrder.get();
    }

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
