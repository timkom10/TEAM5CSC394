package com.driver.workFlowManager.controller;


import com.driver.workFlowManager.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@Controller
@RequestMapping("standardWorkers")
public class StandardWorkerController {

    private final AnnouncementService announcementService;
    private final StandardWorkerService standardWorkerService;

    public StandardWorkerController(AnnouncementService announcementService, StandardWorkerService standardWorkerService) {
        this.announcementService = announcementService;
        this.standardWorkerService = standardWorkerService;
    }

    @GetMapping("index")
    public  String index(Principal principal, Model model) {
        model.addAttribute("name", principal.getName());
        model.addAttribute("announcements", this.announcementService.getAllAnnouncementsByUsername(principal.getName()));
        return "standardWorkers/index";
    }

    @GetMapping("info")
    public String info(Principal principal, Model model) {
        model.addAttribute("workerType", this.standardWorkerService.getByUsername(principal.getName()));
        return "info/info";
    }
}
