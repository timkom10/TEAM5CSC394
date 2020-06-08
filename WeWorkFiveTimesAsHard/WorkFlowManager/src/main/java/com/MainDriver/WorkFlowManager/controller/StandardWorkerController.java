package com.MainDriver.WorkFlowManager.controller;

import com.MainDriver.WorkFlowManager.model.messaging.Message;
import com.MainDriver.WorkFlowManager.model.workers.StandardWorker;
import com.MainDriver.WorkFlowManager.repository.StandardWorkerRepository;
import com.MainDriver.WorkFlowManager.service.AnnouncementService;
import com.MainDriver.WorkFlowManager.service.MessagingService;
import com.MainDriver.WorkFlowManager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@Controller
@RequestMapping("standardWorkers")
public class StandardWorkerController
{
    @Autowired
    MessagingService messagingService;

    @Autowired
    private UserService userService;

    @Autowired
    AnnouncementService announcementService;


    static String usernamePlaceHolder ="";

    private final StandardWorkerRepository standardWorkerRepository;

    public StandardWorkerController(StandardWorkerRepository standardWorkerRepository) {
        this.standardWorkerRepository = standardWorkerRepository;
    }

    @GetMapping("index")
    public  String index(Principal principal, Model model)
    {
        StandardWorker standardWorker = standardWorkerRepository.findByuserName(principal.getName());
        if(standardWorker != null) {
            model.addAttribute(standardWorker);
            model.addAttribute("announcements", standardWorker.getAnnouncements());
        }
        return "standardWorkers/index";
    }

    @GetMapping("feedback")
    public String portal() {
        return "feedback/FeedbackPortal";
    }

    @GetMapping("info")
    public String info(Principal principal, Model model) {
        StandardWorker standardWorker = standardWorkerRepository.findByuserName(principal.getName());
        if(standardWorker != null) {
            model.addAttribute("workerType", standardWorker);
        }
        return "Info/info";
    }

    @GetMapping("messagingPortal")
    public String getMessagingPortal() {
        return "messaging/messagingPortal";
    }

    @GetMapping("searchUserToMessage")
    public String getSearchUserToMessage(Principal principal, Model model, @RequestParam(defaultValue = "") String username) {
        model.addAttribute("users",userService.findByUsernameExcludeSelf(username, principal.getName()));
        return "messaging/searchUserToMessage";
    }

    @GetMapping("composeMessage")
    public String getComposeMessage(Model model, String username) {
        usernamePlaceHolder = username;
        model.addAttribute("to", username);
        model.addAttribute("message", new Message());
        return "messaging/composeMessage";
    }

    @RequestMapping(value = "sendMessage", method = RequestMethod.POST)
    public String getMessageSent(Principal principal, @ModelAttribute("message")Message message) {
        this.messagingService.saveMessage(message,principal.getName(), usernamePlaceHolder);
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

    @GetMapping(value = "viewAnnouncement")
    public String getViewAnnouncement(Principal principal, Model model, Integer announcementID) {
        model.addAttribute("name", principal.getName());
        model.addAttribute("announcement", announcementService.getByUsernameAndAnnouncementId(principal.getName(), announcementID));
        return "announcements/viewAnnouncement";
    }

    @GetMapping(value = "deleteAnnouncement")
    public String getDeleteAnnouncement(Principal principal,Model model, Integer announcementID) {
       announcementService.deleteAnnouncement(principal.getName(), announcementID);
        StandardWorker standardWorker = standardWorkerRepository.findByuserName(principal.getName());
        if(standardWorker != null) {
            model.addAttribute(standardWorker);
            model.addAttribute("announcements", standardWorker.getAnnouncements());
        }
        return "standardWorkers/index";
    }
}
