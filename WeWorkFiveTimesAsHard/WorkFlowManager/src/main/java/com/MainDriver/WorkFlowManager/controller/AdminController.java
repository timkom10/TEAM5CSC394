package com.MainDriver.WorkFlowManager.controller;

import com.MainDriver.WorkFlowManager.model.messaging.Announcement;
import com.MainDriver.WorkFlowManager.model.messaging.Message;
import com.MainDriver.WorkFlowManager.model.workers.Users;
import com.MainDriver.WorkFlowManager.model.workers.Admin;
import com.MainDriver.WorkFlowManager.model.workers.Manager;
import com.MainDriver.WorkFlowManager.model.workers.StandardWorker;
import com.MainDriver.WorkFlowManager.repository.AdminRepository;
import com.MainDriver.WorkFlowManager.service.AnnouncementService;
import com.MainDriver.WorkFlowManager.service.MessagingService;
import com.MainDriver.WorkFlowManager.service.implementation.AdminServiceImp;
import com.MainDriver.WorkFlowManager.service.implementation.ManagerServiceImp;
import com.MainDriver.WorkFlowManager.service.implementation.StandardWorkerServiceImp;
import com.MainDriver.WorkFlowManager.service.implementation.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    AdminServiceImp adminService;

    @Autowired
    UserServiceImp userService;

    @Autowired
    ManagerServiceImp managerService;

    @Autowired
    StandardWorkerServiceImp standardWorkerService;

    @Autowired
    AnnouncementService announcementService;

    @Autowired
    MessagingService messagingService;

    private final AdminRepository adminRepository;

    private static  String usernamePlaceholder ="";

    public AdminController(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @GetMapping("index")
    public String index(Principal principal, Model model) {
        Admin admin = adminService.findByUserName(principal.getName());
        if(admin != null)
        {
            model.addAttribute("admin", admin);
            model.addAttribute("announcements", admin.getAnnouncements());
        }
        return "admin/index";
    }

    @GetMapping("info")
    public String info(Principal principal, Model model) {

        Admin admin = adminService.findByUserName(principal.getName());
        model.addAttribute("workerType", admin);
        return "Info/info";
    }

    @RequestMapping("Hr")
    public String getHR() {
        return "admin/HumanResources";
    }

    @GetMapping("feedback")
    public String portal() {
        return "feedback/FeedbackPortal";
    }

    @RequestMapping(value = "RemoveUser", method = RequestMethod.GET)
    public String getUserSearch(Model model, @RequestParam(defaultValue = "") String username) {
        model.addAttribute("users", userService.findByUsername(username));
        return "admin/RemoveUser";
    }


    @RequestMapping(value = "deleteUser", method = RequestMethod.GET)
    public String getRemoveUser(Model model, String username) {
        userService.removeUser(username);
        model.addAttribute("users", userService.findByUsername(""));
        return "admin/RemoveUser";
    }

    @RequestMapping("addUser")
    public String getAddUser(Model model) {
        model.addAttribute("user", new Users());
        return "admin/addUser";
    }

    @RequestMapping(value = "insertUser", method = RequestMethod.POST)
    public String getInsert(@ModelAttribute("user") Users user, Principal principal, Model model) {
        if(userService.addUser(user)) {
            usernamePlaceholder = user.getUsername();
            if(user.getRoles().equals("STANDARDWORKER")) {
                model.addAttribute("worker", new StandardWorker());
                return "admin/addStandardWorker";
            }
            else if(user.getRoles().equals("MANAGER")) {
                model.addAttribute("manager", new Manager());
                return "admin/addManager";
            }
        }
        return "admin/addUser";
    }

    @RequestMapping(value = "insertStandardWorker", method = RequestMethod.POST)
    public String getInsertStandardWorker(@ModelAttribute("worker") StandardWorker standardWorker,Model model) {
        standardWorkerService.addStandardWorker(userService.getByUsername(this.usernamePlaceholder), standardWorker);
        model.addAttribute("user", new Users());
        return "admin/addUser";
    }

    @RequestMapping(value = "insertManager", method = RequestMethod.POST)
    public String getInsertManager(@ModelAttribute("manager") Manager manager,Model model) {
        managerService.addManager(userService.getByUsername(this.usernamePlaceholder), manager);
        model.addAttribute("user", new Users());
        return "admin/addUser";
    }

    @RequestMapping(value = "searchTeamMember", method = RequestMethod.GET)
    public String getTeamMemberSearch(Model model, @RequestParam(defaultValue = "") String username) {
        model.addAttribute("workers", standardWorkerService.findAllByUsername(username));
        return "admin/searchTeamMembers";
    }

    @GetMapping("alterTeamMember")
    public String getAlterEmployee(Model model, String username)
    {
        this.usernamePlaceholder = username;
        model.addAttribute("worker", standardWorkerService.getByUsername(username));
        return "admin/alterTeamMember";
    }

    @RequestMapping(value = "insertAlteredStandardWorker", method = RequestMethod.POST)
    public String getInsertAlteredStandardWorker(@ModelAttribute("worker") StandardWorker standardWorker,Model model)
    {
        standardWorkerService.insertAlteredStandardWorker(standardWorker, usernamePlaceholder);
        model.addAttribute("user", new Users());
        return "admin/addUser";
    }

    @GetMapping(value = "viewAnnouncement")
    public String getViewAnnouncement(Principal principal, Model model, Integer announcementID) {
        model.addAttribute("name", principal.getName());
        model.addAttribute("announcement", announcementService.getByUsernameAndAnnouncementId(principal.getName(), announcementID));
        return "announcements/viewAnnouncement";
    }

    @GetMapping("messagingPortal")
    public String getMessagingPortal() {
        return "messaging/messagingPortal";
    }

    @GetMapping("searchUserToMessage")
    public String getSearchUserToMessage(Model model, @RequestParam(defaultValue = "") String username) {
        //could probably remove yourself
        model.addAttribute("users",userService.findByUsername(username));
        return "messaging/searchUserToMessage";
    }

    @GetMapping("composeMessage")
    public String getComposeMessage(Model model, String username) {
        usernamePlaceholder = username;
        model.addAttribute("to", username);
        model.addAttribute("message", new Message());
        return "messaging/composeMessage";
    }

    @RequestMapping(value = "sendMessage", method = RequestMethod.POST)
    public String getMessageSent(Principal principal, @ModelAttribute("message")Message message) {
        this.messagingService.saveMessage(message,principal.getName(), usernamePlaceholder);
        return "messaging/messagingPortal";
    }

    @GetMapping(value = "inbox")
    public String getInbox(Model model, @RequestParam(defaultValue = "") String username, Principal principal) {
        model.addAttribute("messages",this.messagingService.getByUserWhereFromIsLike(principal.getName(), username));
        return "messaging/messageInbox";
    }

    @GetMapping(value = "viewMessage")
    public String getViewMessage(Model model, Principal principal, Integer messageId) {
        model.addAttribute("message",this.messagingService.getByUsernameAndMessageId(principal.getName(), messageId));
        return "messaging/viewMessage";
    }

    @GetMapping(value = "deleteMessage")
    public String getDeleteMessage(Model model, Principal principal, @RequestParam(defaultValue = "") String username, Integer messageId) {
        this.messagingService.deleteMessage(principal.getName(), messageId);
        model.addAttribute("messages",this.messagingService.getByUserWhereFromIsLike(principal.getName(), username));
        return "messaging/messageInbox";
    }

    @GetMapping(value = "deleteAnnouncement")
    public String getDeleteAnnouncement(Principal principal,Model model, Integer announcementID) {
        announcementService.deleteAnnouncement(principal.getName(), announcementID);
        Admin admin = adminRepository.findByUserName(principal.getName());
        if(admin != null) {
            model.addAttribute("admin", admin);
            model.addAttribute("announcements", admin.getAnnouncements());
        }
        return "admin/index";
    }

    @RequestMapping(value = "searchTeamToSendAnnouncement", method = RequestMethod.GET)
    public String getSearchTeamToSendAnnouncement(Model model, Principal principal, @RequestParam(defaultValue = "") String username) {
        model.addAttribute("name", principal.getName());
        model.addAttribute("managers", userService.findManagersByUsernameLike(username));
        return "announcements/selectTeamAnnouncement";
    }


    @GetMapping(value = "composeAnnouncement")
    public String getComposeAnnouncement(Principal principal,Model model, String managerUsername) {
        usernamePlaceholder = managerUsername;
        model.addAttribute("name", principal.getName());
        model.addAttribute("announcement", new Announcement());
        return "announcements/composeAnnouncement";
    }

    @RequestMapping(value = "sendAnnouncement", method = RequestMethod.POST)
    public String getSendAnnouncement(Principal principal,Model model, @ModelAttribute("announcement")Announcement announcement) {

        this.announcementService.sendAnnouncement(announcement,principal.getName(), usernamePlaceholder);

        Admin admin = adminRepository.findByUserName(principal.getName());
        if(admin != null) {
            model.addAttribute("admin", admin);
            model.addAttribute("announcements", admin.getAnnouncements());
        }
        return "admin/index";
    }
}
