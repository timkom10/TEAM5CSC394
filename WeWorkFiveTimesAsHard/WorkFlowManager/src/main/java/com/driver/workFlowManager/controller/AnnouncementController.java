package com.driver.workFlowManager.controller;

import com.driver.workFlowManager.model.messaging.Announcement;
import com.driver.workFlowManager.service.*;
import com.driver.workFlowManager.service.implementation.UserServiceImp;
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

    private static  String usernamePlaceholder ="";

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
    public String getDeleteAnnouncement(Principal principal,Model model, Integer announcementID)
    {
        announcementService.deleteAnnouncement(principal.getName(), announcementID);
        String username = principal.getName();

        //find out the users type (worker, manager, admin) and return to their homepage after deletion
        if(this.standardWorkerService.existsByUsername(username)) {
            model.addAttribute(this.standardWorkerService.getByUsername(username));
            model.addAttribute("announcements", announcementService.getAllAnnouncementsByUsername(username));
            return "standardWorkers/index";
        }
        else if(this.managerService.existsByUsername(username)) {
            model.addAttribute(this.managerService.getByUsername(username));
            model.addAttribute("announcements", announcementService.getAllAnnouncementsByUsername(username));
            return "management/index";
        }
        else if(this.adminService.existsByUsername(username)){
            model.addAttribute("admin", this.adminService.findByUserName(username));
            model.addAttribute("announcements", announcementService.getAllAnnouncementsByUsername(username));
            return "admin/index";
        }
        return "error";
    }

    @RequestMapping(value = "searchTeamToSendAnnouncement", method = RequestMethod.GET)
    public String getSearchTeamToSendAnnouncement(Model model, Principal principal, @RequestParam(defaultValue = "") String username) {
        model.addAttribute("name", principal.getName());
        model.addAttribute("managers", this.managerService.findManagersByUsernameLike(username));
        return "announcements/selectTeamAnnouncement";
    }

    @GetMapping(value = "composeAnnouncement")
    public String getComposeAnnouncement(Principal principal,Model model, String managerUsername) {
        if(managerUsername == null) {
            /*Manager sending an announcement to their own team*/
            usernamePlaceholder = principal.getName();
        }
        else {
            /*Admin sending announcement to managerUsername's team*/
            usernamePlaceholder = managerUsername;
        }
        model.addAttribute("name", principal.getName());
        model.addAttribute("announcement", new Announcement());
        return "announcements/composeAnnouncement";
    }


    @RequestMapping(value = "sendAnnouncement", method = RequestMethod.POST)
    public String getSendAnnouncement(Principal principal,Model model, @ModelAttribute("announcement")Announcement announcement) {
        this.announcementService.sendAnnouncement(announcement, principal.getName(), usernamePlaceholder);

        /*Check whether it was a manager or an admin that sent this announcement*/
        String currentUser = principal.getName();
        if(this.managerService.existsByUsername(currentUser)) {
            model.addAttribute("manager", this.managerService.getByUsername(currentUser));
            model.addAttribute("announcements", announcementService.getAllAnnouncementsByUsername(currentUser));
            return "management/index";
        }
        else if(this.adminService.existsByUsername(currentUser)) {
            model.addAttribute("admin", this.adminService.findByUserName(currentUser));
            model.addAttribute("announcements", announcementService.getAllAnnouncementsByUsername(currentUser));
            return "admin/index";
        }
        return "error";
    }
}
