package no.ntnu.bicycle.controller.rest;

import no.ntnu.bicycle.model.Bicycle;
import no.ntnu.bicycle.model.Customer;
import no.ntnu.bicycle.service.BicycleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/bicycle")
public class BicycleController {

    BicycleService bicycleService;

    public BicycleController(BicycleService bicycleService){
        this.bicycleService = bicycleService;
    }

    @GetMapping
    public List<Bicycle> getAllBicycles() {
        return bicycleService.getAllBicycles();
    }
}
