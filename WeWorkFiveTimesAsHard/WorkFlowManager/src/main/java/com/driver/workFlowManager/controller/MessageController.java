package com.driver.workFlowManager.controller;

import com.driver.workFlowManager.model.messaging.Message;
import com.driver.workFlowManager.service.MessagingService;
import com.driver.workFlowManager.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

/*
    All (authenticated) users can send a message; to condense the copy+ paste of code in the
    Admin, Manager, Worker controllers, the core-logic of sending, receiving and viewing a message
    Is implemented here.
 */
@Controller
@RequestMapping({"standardWorkers", "management", "admin"})
public class MessageController {
    private final UserService userService;
    private final MessagingService messagingService;

    public MessageController(UserService userService, MessagingService messagingService) {
        this.userService = userService;
        this.messagingService = messagingService;
    }

    @RequestMapping("messagingPortal")
    public String getMessagingPortal(Principal principal, Model model) {
        model.addAttribute("userType", this.userService.getUserType(principal.getName()));
        model.addAttribute("name", principal.getName());
        return "messaging/messagingPortal";
    }

    @GetMapping(value = "inbox")
    public String getInbox(Model model, @RequestParam(defaultValue = "") String username, Principal principal) {
        model.addAttribute("userType", this.userService.getUserType(principal.getName()));
        model.addAttribute("name", principal.getName());
        model.addAttribute("messages",this.messagingService.getByUserWhereFromIsLike(principal.getName(), username));
        return "messaging/messageInbox";
    }

    @GetMapping("searchUserToMessage")
    public String getSearchUserToMessage(Principal principal, Model model, @RequestParam(defaultValue = "") String username) {
        model.addAttribute("userType", this.userService.getUserType(principal.getName()));
        model.addAttribute("name", principal.getName());
        model.addAttribute("users", userService.findByUsername(username));
        return "messaging/searchUserToMessage";
    }

    @GetMapping("composeMessage")
    public String getComposeMessage(Principal principal, Model model, String username) {
        model.addAttribute("userType", this.userService.getUserType(principal.getName()));
        model.addAttribute("name", principal.getName());
        model.addAttribute("to", username);
        model.addAttribute("message", new Message());
        return "messaging/composeMessage";
    }

    @RequestMapping(value = "sendMessage", method = RequestMethod.POST)
    public String getMessageSent(Principal principal, Model model,@ModelAttribute("message")Message message, String toUsername) {
        this.messagingService.saveMessage(message,principal.getName(), toUsername);
        return getMessagingPortal(principal, model) ;
    }

    @GetMapping(value = "viewMessage")
    public String getViewMessage(Model model, Principal principal, Integer messageId) {
        model.addAttribute("userType", this.userService.getUserType(principal.getName()));
        model.addAttribute("name", principal.getName());
        model.addAttribute("message",this.messagingService.getByUsernameAndMessageId(principal.getName(), messageId));
        return "messaging/viewMessage";
    }

    @GetMapping(value = "deleteMessage")
    public String getDeleteMessage(Model model, Principal principal, Integer messageId) {
        this.messagingService.deleteMessage(principal.getName(), messageId);
        return getInbox(model, null, principal);
    }
}
