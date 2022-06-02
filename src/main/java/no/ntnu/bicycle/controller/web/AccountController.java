package no.ntnu.bicycle.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * This is a REST API controller - part of the backend
 */
@Controller
@RequestMapping("/account")
public class AccountController {
    /**
     * Responds to HTTP get /
     *
     * @return Name of the template to render
     */
    @GetMapping()
    public String getAccount(Model model) {
        return "HTML/Account";
    }

    /**
     * Respond to HTTP get
     * @return account address
     */
    @GetMapping("/address")
    public String getAccountAddress(){

        return "HTML/Account-address";
    }

    /**
     * Responds to HTTP get
     * @return account orders
     */
    @GetMapping("/orders")
    public String getAccountOrders(){

        return "HTML/Account-orders";
    }

    @GetMapping("/all-products")
    public String getProducts() {
        return "HTML/Admin-products";
    }

    @GetMapping("/all-orders")
    public String getOrders() {
        return "HTML/Admin-orders";
    }

    @GetMapping("/all-bikes")
    public String getBikes() {
        return "HTML/Admin-bikes";
    }
}
