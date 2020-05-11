package com.MainDriver.WorkFlowManager.Controller;

import com.MainDriver.WorkFlowManager.repository.UsersRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UsersController {
    private final UsersRepository usersRepository;

    public UsersController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @RequestMapping("/Users")
    public String getUsers(Model model)
    {
        model.addAttribute("users", usersRepository.findAll());
        return "UsersView/AllKnownUsers";
    }

}
