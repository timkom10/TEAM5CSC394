package com.MainDriver.WorkFlowManager.controller;

import com.MainDriver.WorkFlowManager.model.messaging.Message;
import com.MainDriver.WorkFlowManager.model.workers.StandardWorker;
import com.MainDriver.WorkFlowManager.repository.StandardWorkerRepository;
import com.MainDriver.WorkFlowManager.service.MessagingService;
import com.MainDriver.WorkFlowManager.service.UserService;
import com.MainDriver.WorkFlowManager.service.implementation.StandardWorkerServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("StandardWorkers")
public class StandardWorkerController
{
    @Autowired
    MessagingService messagingService;

    @Autowired
    private UserService userService;

    @Autowired
    private StandardWorkerServiceImp standardWorkerService;

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
        }
        return "StandardWorkers/index";
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

    @RequestMapping(value = "removeAnnouncement", method = RequestMethod.GET)
    public String getRemoveAnnouncement(Model model, Principal principal, Long announcementId) {

        StandardWorker standardWorker = standardWorkerRepository.findByuserName(principal.getName());
        if(standardWorker != null) {
            model.addAttribute(standardWorker);
        }
        return "StandardWorkers/index";
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
        usernamePlaceHolder = username;
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
}
