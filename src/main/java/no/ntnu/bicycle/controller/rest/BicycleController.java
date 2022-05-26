package no.ntnu.bicycle.controller.rest;

import no.ntnu.bicycle.model.Bicycle;
import no.ntnu.bicycle.model.BicycleRentalOrder;
import no.ntnu.bicycle.model.Customer;
import no.ntnu.bicycle.service.BicycleRentalOrderService;
import no.ntnu.bicycle.service.BicycleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST API controller for bicycle.
 */
@RestController
@RequestMapping("/api/bicycle")
public class BicycleController {

    BicycleService bicycleService;
    BicycleRentalOrderService bicycleRentalOrderService;

    /**
     * Constructor with bicycle service
     * @param bicycleService bicycle service
     */
    public BicycleController(BicycleService bicycleService){
        this.bicycleService = bicycleService;
    }

    /**
     * Gets all bicycles
     * @return list of all bicycles
     */
    @GetMapping
    public List<Bicycle> getAllBicycles() {
        return bicycleService.getAllBicycles();
    }

    /**
     * Gets all available bicycles
     * @return list of all available bicycles
     */
    @GetMapping
    @RequestMapping("/available")
    public List<Bicycle> getAllAvailableBicycles() {
        return bicycleService.getAllAvailableBicycles();
    }
}
