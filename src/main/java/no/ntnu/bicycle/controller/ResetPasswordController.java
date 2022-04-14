package no.ntnu.bicycle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ResetPasswordController {
    /**
     * Responds to HTTP get /
     *
     * @return Name of the template to render
     */
    @GetMapping("/forgot-password")
    public String getResetPassword(Model model) {

        return "HTML/forgot-password";
    }
}
