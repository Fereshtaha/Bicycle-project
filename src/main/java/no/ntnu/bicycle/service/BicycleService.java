package no.ntnu.bicycle.service;

import no.ntnu.bicycle.model.Bicycle;
import no.ntnu.bicycle.model.Customer;
import no.ntnu.bicycle.repository.BicycleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

/**
 * Business logic related to bicycles
 */
@Service
public class BicycleService {
    @Autowired
    BicycleRepository bicycleRepository;

    /**
     * Constructor with the parameter bicycleRepository
     * @param bicycleRepository BicycleRepository
     */
    public BicycleService(BicycleRepository bicycleRepository) {
        this.bicycleRepository = bicycleRepository;
    }

    /**
     * Finds bicycle by ID
     * @param id long. Bicycle id
     * @return The bicycle or nothing if none is found by that ID
     */
    public Bicycle findBicycleById(long id) {
        Optional<Bicycle> bicycle = bicycleRepository.findById(id);

        return bicycle.get();
    }

    public void setStatusToAvailable(long id){
        try {
            Bicycle bicycle = findBicycleById(id);
            bicycle.setStatusToAvailable();

            bicycleRepository.save(bicycle);
        } catch (NoSuchElementException e) {

        }
    }

    /**
     * Adds a biccycle to the application (database)
     * @param bicycle Bicycle
     * @return true when bicycle added, false on error
     */
    public boolean addBicycle(Bicycle bicycle) {
        boolean added = false;
        if (bicycle != null) {
            try {
                findBicycleById(bicycle.getId());
            } catch (NoSuchElementException e ) {
                bicycleRepository.save(bicycle);
                added = true;
            }
        }
        return added;
       /* try {
            findBicycleById(bicycle.getId());
            bicycleRepository.save(bicycle);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }*/
    }

    /**
     * Gets all bicycles
     * @return list of all bicycles
     */
    public List<Bicycle> getAllBicycles() {
        return (List<Bicycle>) bicycleRepository.findAll();
    }

    /**
     * Gets all available bicycles
     * @return returns the bicycle if it is available
     */
    public List<Bicycle> getAllAvailableBicycles() {
        ArrayList<Bicycle> list = new ArrayList<>();
        bicycleRepository.findAll().forEach(bicycle -> {
            if (bicycle.isAvailable()) {
                list.add(bicycle);
            }
        });
        return list;
    }

    /**
     * Sets the status to rented
     * @param bicycle Bicycle. The bicycle that needs to be set as rented.
     * @return true if it is rented, false if it is available
     */
    public boolean setStatusToRented(Bicycle bicycle) {
        try {
            findBicycleById(bicycle.getId());
            bicycle.setStatusToRented();
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void updateBicycle(Bicycle bicycle) {
        bicycleRepository.save(bicycle);

    }

    /**
     * Deletes a bike
     * @param bikeId int. Bicycle Id that gets deleted
     * @return true if bike is deleted, and false if it does not exist
     */
    public boolean deleteBicycle(int bikeId) {
        Optional<Bicycle> bicycle = bicycleRepository.findById((long) bikeId);
        bicycle.ifPresent(value -> bicycleRepository.delete(value));
        return bicycle.isPresent();
    }
}