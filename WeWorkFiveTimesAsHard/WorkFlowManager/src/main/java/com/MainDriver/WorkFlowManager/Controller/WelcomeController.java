package com.MainDriver.WorkFlowManager.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WelcomeController {

    @RequestMapping("/")
    public String getWelcome() { return "WelcomeView/LandingPage"; }

    @RequestMapping("/TermsOfService")
    public String getTOS()
    {
        return "WelcomeView/TOS";
    }

    @RequestMapping("/About")
    public String getAbout() { return "WelcomeView/About"; }

    @RequestMapping("/Login")
    public String getLogin(Model model) {
        return "WelcomeView/Login";
    }

    @RequestMapping("/user")
    public String user() {
        return "WelcomeView/About";
    }

    @GetMapping("/admin")
    public String admin()
    {
        return ("<h1> Welcome Admin </h1>");
    }
}
