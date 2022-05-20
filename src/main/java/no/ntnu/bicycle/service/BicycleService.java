package no.ntnu.bicycle.service;

import no.ntnu.bicycle.model.Bicycle;
import no.ntnu.bicycle.repository.BicycleRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BicycleService {
    BicycleRepository bicycleRepository;

    public BicycleService(BicycleRepository bicycleRepository){
        this.bicycleRepository = bicycleRepository;
    }

    public Bicycle findBicycleById(long id){
        Optional<Bicycle> bicycle = bicycleRepository.findById(id);

        return bicycle.get();
    }

    public boolean addBicycle(Bicycle bicycle){
        try{
            findBicycleById(bicycle.getId());
            bicycleRepository.save(bicycle);
            return true;
        }catch (NoSuchElementException e){
            return false;
        }
    }

    public List<Bicycle> getAllBicycles(){
        return (List<Bicycle>) bicycleRepository.findAll();
    }

    public List<Bicycle> getAllAvailableBicycles(){
        ArrayList<Bicycle> list = new ArrayList<>();
        bicycleRepository.findAll().forEach(bicycle -> {
            if (bicycle.isAvailable()){
                list.add(bicycle);
            }
        });
        return list;
    }

    public boolean setStatusToRented(Bicycle bicycle){
        try{
            findBicycleById(bicycle.getId());
            bicycle.setStatusToRented();
            return true;
        }catch (NoSuchElementException e){
            return false;
        }
    }
}
