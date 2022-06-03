package no.ntnu.bicycle.controller.web;

import no.ntnu.bicycle.model.Customer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * This is a REST API controller - part of the backend
 */
@Controller
@RequestMapping("/")
public class AccountController {
    Customer customer = new Customer();
    /**
     * Responds to HTTP get /
     *
     * @return Name of the template to render
     */
    @GetMapping("/account")
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

    @GetMapping("/admin")
    public String getLoginSuccessAdmin(Model model) {
        return "HTML/Admin-users";
    }

    @GetMapping("account/all-products")
    public String getProducts() {
        return "HTML/Admin-products";
    }

    @GetMapping("account/all-orders")
    public String getOrders() {
        return "HTML/Admin-orders";
    }

    @GetMapping("account/all-bikes")
    public String getBikes() {
        return "HTML/Admin-bikes";
    }
}
