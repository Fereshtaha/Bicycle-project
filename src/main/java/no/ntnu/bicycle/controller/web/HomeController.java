package no.ntnu.bicycle.controller.web;

import javax.validation.Valid;
import no.ntnu.bicycle.model.Customer;
import no.ntnu.bicycle.model.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * MVC controller for the main page(s). Returns HTML pages, not JSON!
 */
@Controller
public class HomeController {

    /**
     * Responds to HTTP get /
     *
     * @return Name of the template to render
     */
    @GetMapping("/")
    public String getHome() {

        return "index";
    }

    /**
     * Responds to HTTP get
     * @return login successful for user
     */
    @GetMapping("/user")
    public String getLoginSuccessUser() {

        return "HTML/loginSuccessUser";
    }

    @GetMapping("/admin")
    public String getLoginSuccessAdmin(Model model) {
        return "HTML/Admin-users";
    }
}
