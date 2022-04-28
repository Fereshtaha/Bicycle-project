package no.ntnu.bicycle.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
