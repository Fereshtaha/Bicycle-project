package no.ntnu.bicycle.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UpdatePasswordController {
    /**
     * Responds to HTTP get /
     *
     * @return Name of the template to render
     */
    @GetMapping("/update-password")
    public String getUpdatePassword(Model model) {

        return "HTML/update-password";
    }
}
