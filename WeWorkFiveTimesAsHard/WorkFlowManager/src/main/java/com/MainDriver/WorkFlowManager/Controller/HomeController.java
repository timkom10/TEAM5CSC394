package com.MainDriver.WorkFlowManager.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping("index")
    public String getWelcome() { return "index"; }

    @RequestMapping("/login")
    public String getLogin() {
        return "login";
    }

    @RequestMapping("/About")
    public String getAbout() {
        return "WelcomeView/About";
    }

    @RequestMapping("/TOS")
    public String getTOS() {
        return "WelcomeView/TOS";
    }


}
