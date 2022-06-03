package no.ntnu.bicycle.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * This is a REST API controller - part of the backend
 */
@Controller
public class FooterController {
    /**
     * Responds to HTTP get /
     *
     * @return Name of the template to render
     */
    @GetMapping("/about-us")
    public String getAboutUs(Model model) {

        return "HTML/footer/Om-oss";
    }

    /**
     * Return a page from the footer
     * @param model
     * @return
     */
    @GetMapping("/customer-service")
    public String getCustomerService(Model model) {
        return "HTML/footer/Customer-Service";
    }

    /**
     * Return a page from the footer
     * @return delivery information
     */
    @GetMapping("/delivery-information")
    public String getDeliveryInfo() {
        return "HTML/footer/Delivery-information";
    }

    /**
     * Return a page from the footer
     * @return terms & policy
     */
    @GetMapping("/terms-policy")
    public String getTermsPolicy() {
        return "HTML/footer//Terms-policy";
    }

    /**
     * Return a page from the footer
     * @return contact us
     */
    @GetMapping("/contact-us")
    public String getContactUs() {
        return "HTML/footer//Contact-us";
    }
}
