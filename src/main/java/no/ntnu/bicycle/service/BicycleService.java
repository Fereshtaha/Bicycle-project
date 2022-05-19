package no.ntnu.bicycle.service;

import no.ntnu.bicycle.model.Bicycle;
import no.ntnu.bicycle.repository.BicycleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
        boolean added = false;
        try{
            findBicycleById(bicycle.getId());
        }catch (NoSuchElementException e){
            bicycleRepository.save(bicycle);
            added = true;
        }

        return added;
    }

    public List<Bicycle> getAllBicycles(){
        return (List<Bicycle>) bicycleRepository.findAll();
    }
}
