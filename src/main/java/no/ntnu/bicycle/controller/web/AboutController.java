package no.ntnu.bicycle.controller.web;

import org.springframework.boot.Banner;
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

        return "HTML/footer/Om-oss";
    }

    @GetMapping("/customer-service")
    public String getCustomerService(Model model) {
        return "HTML/footer/Customer-Service";
    }

    @GetMapping("/delivery-information")
    public String getDelivaryInfo(Model model) {
        return "HTML/footer/Delivery-information";
    }

    @GetMapping("/terms-policy")
    public String getTermsPolicy(Model model) {
        return "HTML/footer//Terms-policy";
    }

    @GetMapping("/contact-us")
    public String getContactUs(Model model) {
        return "HTML/footer//Contact-us";
    }
}
