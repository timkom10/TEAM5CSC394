package com.driver.workFlowManager.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping("index")
    public String getWelcome() { return "index"; }

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @RequestMapping("/About")
    public String getAbout() {
        return "welcomePages/About";
    }

    @RequestMapping("/TOS")
    public String getTOS() {
        return "welcomePages/TOS";
    }

    @RequestMapping("/success")
    public void loginPageRedirect(HttpServletRequest request, HttpServletResponse response, Authentication authResult) throws IOException, ServletException {

        String role =  authResult.getAuthorities().toString();

        if(role.contains("ROLE_ADMIN")){
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/admin/index"));
        }
        else if(role.contains("ROLE_MANAGER")) {
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/management/index"));
        }
        else if(role.contains("ROLE_STANDARDWORKER")) {
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/standardWorkers/index"));
        }
    }
}
