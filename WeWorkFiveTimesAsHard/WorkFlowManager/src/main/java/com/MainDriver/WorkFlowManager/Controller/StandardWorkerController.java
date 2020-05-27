package com.MainDriver.WorkFlowManager.Controller;

import com.MainDriver.WorkFlowManager.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("StandardWorkers")
public class StandardWorkerController {

    private UserRepository userRepository;

    public StandardWorkerController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("index")
    public  String index(Principal principal, Model model)
    {
        System.out.println(principal.getName());

        System.out.println("REQUESTING SENT");
        return "StandardWorkers/index";
    }
}
