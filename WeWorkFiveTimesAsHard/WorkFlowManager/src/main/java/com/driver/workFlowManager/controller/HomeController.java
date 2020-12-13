package com.driver.workFlowManager.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping({"/","index"})
    public String getWelcome() { return "welcomePages/index"; }

    @GetMapping("login")
    public String getLogin() {
        return "welcomePages/login";
    }

    @GetMapping("about")
    public String getAbout() {
        return "welcomePages/about";
    }

    @GetMapping("tos")
    public String getTOS() {
        return "welcomePages/tos";
    }

    @RequestMapping("/success")
    public void loginPageRedirect(HttpServletRequest request, HttpServletResponse response, Authentication authResult) throws IOException {

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
