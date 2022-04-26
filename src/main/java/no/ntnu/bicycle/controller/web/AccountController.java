package no.ntnu.bicycle.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/address")
    public String getAccountAddress(Model model){

        return "HTML/Account-address";
    }

    @GetMapping("orders")
    public String getAccountOrders(Model model){

        return "HTML/Account-orders";
    }
}
