package com.MainDriver.WorkFlowManager.Controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

    // You can inject default messages through applications.properties
    @Value("${welcome.message}")
    private String message;

    @GetMapping("/") //http://localhost:8080 , someone connected to the local host, direct them to the homepage
    public String main(Model model) {
        model.addAttribute("message", message); //display the message
        return "HomePage";  //<= return the hompage.html to them.
    }
}