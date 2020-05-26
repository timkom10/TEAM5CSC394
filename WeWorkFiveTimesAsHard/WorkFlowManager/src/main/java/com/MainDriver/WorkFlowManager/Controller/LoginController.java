package com.MainDriver.WorkFlowManager.Controller;

import com.MainDriver.WorkFlowManager.Comands.LoginCommand;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class LoginController {

    @RequestMapping("/Login")
    public String getLogin(Model model) {
        model.addAttribute("LoginCommand", new LoginCommand());
        return "WelcomeView/Login";
    }

    @RequestMapping("logout-success")
    public String yourLoggedOut(){
        return "WelcomeView/Logout-Success";
    }

    public String doLogin(@Valid LoginCommand loginCommand, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "WelcomeView/Login";
        }

        /*
            Add auth here for user type
         */
        return "redirect:index";
    }
}
