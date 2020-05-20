package com.MainDriver.WorkFlowManager.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WelcomeController {

    @RequestMapping("/")
    public String getWelcome()
    {
        return "WelcomeView/LandingPage";
    }

    @RequestMapping("/login")
    public String getLogin()
    {
        return "WelcomeView/Login";
    }
}
