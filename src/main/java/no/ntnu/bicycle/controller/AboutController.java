package no.ntnu.bicycle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutController {
    /**
     * Responds to HTTP get /
     *
     * @return Name of the template to render
     */
    @GetMapping("/about-us")
    public String getAboutUs(Model model) {

        return "HTML/Om-oss";
    }
}
