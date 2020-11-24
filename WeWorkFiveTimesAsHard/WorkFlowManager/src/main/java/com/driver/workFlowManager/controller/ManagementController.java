package com.driver.workFlowManager.controller;

import com.driver.workFlowManager.service.*;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@Controller
@RequestMapping("management")
public class ManagementController {

    private final AnnouncementService announcementService;
    private final ManagerService managerService;

    public ManagementController(AnnouncementService announcementService, ManagerService managerService) {
        this.announcementService = announcementService;
        this.managerService = managerService;
    }

    @GetMapping("index")
    public  String index(Principal principal, Model model) {
        model.addAttribute("name", principal.getName());
        model.addAttribute("announcements", announcementService.getAllAnnouncementsByUsername(principal.getName()));
        return "management/index";
    }

    @GetMapping("info")
    public String info(Principal principal, Model model) {
        model.addAttribute("workerType", this.managerService.getByUsername(principal.getName()));
        return "info/info";
    }
}
