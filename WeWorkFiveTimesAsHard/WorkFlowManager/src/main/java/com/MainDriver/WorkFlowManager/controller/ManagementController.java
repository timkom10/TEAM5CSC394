package com.MainDriver.WorkFlowManager.controller;

import com.MainDriver.WorkFlowManager.model.workers.Manager;
import com.MainDriver.WorkFlowManager.model.workers.StandardWorker;
import com.MainDriver.WorkFlowManager.repository.ManagerRepository;
import org.springframework.ui.Model;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("management")
public class ManagementController {

    private final ManagerRepository managerRepository;

    public ManagementController(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    @GetMapping("index")
    public  String index(Principal principal, Model model)
    {
        model.addAttribute("manager", managerRepository.findByUserName(principal.getName()));
        return "management/index";
    }

    @GetMapping("info")
    public String info(Principal principal, Model model) {
        Manager manager = managerRepository.findByUserName(principal.getName());
        if(manager != null) {
            model.addAttribute("workerType", manager);
        }
        return "Info/info";
    }

    @GetMapping("feedback")
    public String portal() {
        return "feedback/FeedbackPortal";
    }



}
