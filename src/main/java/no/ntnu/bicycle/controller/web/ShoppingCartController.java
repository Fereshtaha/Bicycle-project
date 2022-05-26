package no.ntnu.bicycle.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * This is a REST API controller - part of the backend
 */
@Controller
public class ShoppingCartController {
    /**
     * Responds to HTTP get /
     *
     * @return Name of the template to render
     */
    @GetMapping("/shopping-cart")
    public String getShoppingCart(Model model) {

        return "HTML/shopping-cart";
    }
}
