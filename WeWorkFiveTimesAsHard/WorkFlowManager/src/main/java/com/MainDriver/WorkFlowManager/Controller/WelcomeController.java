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

    @RequestMapping("/Login")
    public String getLogin()
    {
        return "WelcomeView/Login";
    }

    @RequestMapping("/TermsOfService")
    public String getTOS()
    {
        return "WelcomeView/TOS";
    }
}
