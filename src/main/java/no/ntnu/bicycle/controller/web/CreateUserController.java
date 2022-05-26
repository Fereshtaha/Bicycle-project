package no.ntnu.bicycle.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
/**
 * This is a REST API controller - part of the backend
 */
@Controller
public class CreateUserController {
    /**
     * Responds to HTTP get /
     *
     * @return Name of the template to render
     */
    @GetMapping("/create-user")
    public String getAboutUs(Model model) {

        return "HTML/Create-user";
    }
}
