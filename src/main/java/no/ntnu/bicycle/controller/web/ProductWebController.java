package no.ntnu.bicycle.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.websocket.server.PathParam;

@Controller
public class ProductWebController {
    /**
     * Responds to HTTP get /
     *
     * @return Name of the template to render
     */
    @GetMapping("/products")
    public String getProductWeb(Model model) {

        return "HTML/ProductPage";
    }

    @GetMapping("/products/{id}")
    public String getProductDetailsWeb(@PathParam("costumer") @PathVariable("id") int customerId,Model model) {
        return "HTML/ProductDetailsPage";
    }

    @GetMapping("/products/customize")
    public String getProductCustomizeWeb(){
        return "HTML/BikeCustomization";
    }
}
