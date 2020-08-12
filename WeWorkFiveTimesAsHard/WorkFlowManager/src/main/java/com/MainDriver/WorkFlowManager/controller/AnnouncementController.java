package com.MainDriver.WorkFlowManager.controller;

import com.MainDriver.WorkFlowManager.model.workers.StandardWorker;
import com.MainDriver.WorkFlowManager.model.workers.Users;
import com.MainDriver.WorkFlowManager.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.transaction.Transactional;
import java.security.Principal;

/*
    Any authenticated user can view an announcement (if they have one), managers and admins
    Can send an announcement
 */

@Controller
@RequestMapping({"standardWorkers", "management"})
public class AnnouncementController {

    @Autowired
    AnnouncementService announcementService;

    @Autowired
    private StandardWorkerService standardWorkerService;

    @Autowired
    private ManagerService managerService;

    @Autowired
    private AdminService adminService;

    @GetMapping(value = "viewAnnouncement")
    @Transactional
    public String getViewAnnouncement(Principal principal, Model model, Integer announcementID) {
        model.addAttribute("name", principal.getName());
        model.addAttribute("announcement", announcementService.getByUsernameAndAnnouncementId(principal.getName(), announcementID));
        return "announcements/viewAnnouncement";
    }

    @GetMapping(value = "deleteAnnouncement")
    @Transactional
    public String getDeleteAnnouncement(Principal principal,Model model, Integer announcementID)
    {
        announcementService.deleteAnnouncement(principal.getName(), announcementID);
        String username = principal.getName();

        //find out the users type (worker, manager, admin)
        if(this.standardWorkerService.existsByUsername(username)) {
            model.addAttribute(this.standardWorkerService.getByUsername(username));
        }
        else if(this.managerService.existsByUsername(username)) {
            model.addAttribute(this.managerService.getByUsername(username));
        }
        else if(this.adminService.existsByUsername(username)){
            model.addAttribute(this.adminService.findByUserName(username));
        }

        model.addAttribute("announcements", announcementService.getAllAnnouncementsByUsername(username));
        return "standardWorkers/index";
    }


}
