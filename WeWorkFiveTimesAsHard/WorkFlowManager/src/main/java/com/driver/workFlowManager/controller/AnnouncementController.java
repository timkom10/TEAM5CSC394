package com.driver.workFlowManager.controller;

import com.driver.workFlowManager.model.messaging.Announcement;
import com.driver.workFlowManager.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

/*
    Any authenticated user can view or delete an announcement (if they have one), managers and admins
    Can send an announcement to either their own team (if the user is a manager), or choose a team
    To send an announcement to (if the user is an admin)
 */

@Controller
@RequestMapping({"standardWorkers", "management", "admin"})
public class AnnouncementController {
    private final AnnouncementService announcementService;
    private final StandardWorkerService standardWorkerService;
    private final ManagerService managerService;
    private final AdminService adminService;

    public AnnouncementController(AnnouncementService announcementService, StandardWorkerService standardWorkerService, ManagerService managerService, AdminService adminService) {
        this.announcementService = announcementService;
        this.standardWorkerService = standardWorkerService;
        this.managerService = managerService;
        this.adminService = adminService;
    }

    @GetMapping(value = "viewAnnouncement")
    public String getViewAnnouncement(Principal principal, Model model, Integer announcementID) {
        model.addAttribute("name", principal.getName());
        model.addAttribute("announcement", announcementService.getByUsernameAndAnnouncementId(principal.getName(), announcementID));
        return "announcements/viewAnnouncement";
    }

    @GetMapping(value = "deleteAnnouncement")
    public String getDeleteAnnouncement(Principal principal,Integer announcementID) {
        announcementService.deleteAnnouncement(principal.getName(), announcementID);
        if(this.standardWorkerService.existsByUsername(principal.getName())) {
            return "redirect:/standardWorkers/index";
        }
        else if(this.managerService.existsByUsername(principal.getName())) {
            return "redirect:/management/index";
        }
        else if(this.adminService.existsByUsername(principal.getName())){
            return "redirect:/admin/index";
        }
        return "error";
    }

    @GetMapping(value = "searchTeamToSendAnnouncement")
    public String getSearchTeamToSendAnnouncement(Model model, Principal principal, @RequestParam(defaultValue = "") String username) {
        model.addAttribute("name", principal.getName());
        model.addAttribute("managers", this.managerService.findManagersByUsernameLike(username));
        return "announcements/selectTeamAnnouncement";
    }

    @GetMapping(value = "composeAnnouncement")
    public String getComposeAnnouncement(Principal principal,Model model, String managerUsername) {
        model.addAttribute("managerUsername", managerUsername);
        model.addAttribute("name", principal.getName());
        model.addAttribute("announcement", new Announcement());
        return "announcements/composeAnnouncement";
    }

    @RequestMapping(value = "sendAnnouncement", method = RequestMethod.POST)
    public String getSendAnnouncement(Principal principal, @ModelAttribute("announcement")Announcement announcement, String manUsername) {
        this.announcementService.sendAnnouncement(announcement, principal.getName(), manUsername);

        if(this.managerService.existsByUsername(principal.getName())) {
            return "redirect:/management/index";
        }
        else if(this.adminService.existsByUsername(principal.getName())) {
            return "redirect:/admin/index";
        }
        return "error";
    }
}
