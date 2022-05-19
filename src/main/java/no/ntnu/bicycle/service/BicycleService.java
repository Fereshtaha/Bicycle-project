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

    public boolean findBicycleById(long id){
        Optional<Bicycle> bicycle = bicycleRepository.findById(id);

        return bicycle.isPresent();
    }

    public boolean addBicycle(Bicycle bicycle){
        if(!findBicycleById(bicycle.getId())){
            bicycleRepository.save(bicycle);
            return true;
        }else {
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
        if(findBicycleById(bicycle.getId())){
            bicycle.setStatusToRented();
            return true;
        }else{
            return false;
        }
    }
}
