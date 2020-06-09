package com.MainDriver.WorkFlowManager.controller;

import com.MainDriver.WorkFlowManager.model.feedback.Feedback;
import com.MainDriver.WorkFlowManager.model.messaging.Message;
import com.MainDriver.WorkFlowManager.model.projects.Project;
import com.MainDriver.WorkFlowManager.model.workers.StandardWorker;
import com.MainDriver.WorkFlowManager.repository.AllFeedbackRepository;
import com.MainDriver.WorkFlowManager.repository.StandardWorkerRepository;
import com.MainDriver.WorkFlowManager.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;
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

    @Autowired
    StandardWorkerService standardWorkerService;

    @Autowired
    FeedbackService feedbackService;

    @Autowired
    AllFeedbackRepository allFeedbackRepository;

    static String usernamePlaceHolder ="";

    private final StandardWorkerRepository standardWorkerRepository;

    public StandardWorkerController(StandardWorkerRepository standardWorkerRepository) {
        this.standardWorkerRepository = standardWorkerRepository;
    }

    @GetMapping("index")
    @Transactional
    public  String index(Principal principal, Model model)
    {
        StandardWorker standardWorker = this.standardWorkerService.getByUsername(principal.getName());
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
    @Transactional
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
        model.addAttribute("users", userService.findByUsername(username));
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

    @GetMapping(value = "viewProject")
    public String getViewProject(Principal principal,Model model) {
        Project project = this.standardWorkerService.getStandardWorkerProject(principal.getName());
        if(project != null) {
            model.addAttribute("name", principal.getName());
            model.addAttribute("workers", project.getTeamMembers());
            model.addAttribute("project", project);
            model.addAttribute("milestones", project.getMilestones());
            model.addAttribute("completedTasks", project.getCompletedTasksReverse());
        }
        return "project/projectHomepage";
    }

    @GetMapping(value = "projectInfo")
    public String getProjectInfo(Principal principal,Model model) {
        Project project = this.standardWorkerService.getStandardWorkerProject(principal.getName());
        if(project != null) {
            model.addAttribute("name", principal.getName());
            model.addAttribute("project", project);
        }
        return "project/projectInfo";
    }

    @GetMapping(value = "personalFeedback")
    @Transactional
    public String getPersonalFeedback(Principal principal,Model model) {
        StandardWorker standardWorker = this.standardWorkerRepository.findByuserName(principal.getName());
        if(standardWorker != null) {
            model.addAttribute("name", principal.getName());
            model.addAttribute("feedbacks", standardWorker.getPersonalFeedback());
        }
        return "feedback/personalFeedback";
    }

    @GetMapping(value = "composeFeedback")
    @Transactional
    public String getComposeFeedback(Principal principal,Model model, String to) {
        StandardWorker standardWorker = this.standardWorkerRepository.findByuserName(principal.getName());
        if(standardWorker != null) {
            usernamePlaceHolder = to;
            model.addAttribute("name", principal.getName());
            model.addAttribute("to", to);
            model.addAttribute("feedback", new Feedback());
        }
        return "feedback/composeFeedback";
    }

    @RequestMapping(value = "feedbackSent", method = RequestMethod.POST)
    @Transactional
    public String getFeedbackSent(Principal principal,Model model, @ModelAttribute("feedback")Feedback feedback) {
        this.feedbackService.addFeedback(feedback,usernamePlaceHolder,principal.getName());
        Project project = this.standardWorkerService.getStandardWorkerProject(principal.getName());
        if(project != null) {
            model.addAttribute("name", principal.getName());
            model.addAttribute("workers", project.getTeamMembers());
            model.addAttribute("project", project);
            model.addAttribute("milestones", project.getMilestones());
            model.addAttribute("completedTasks", project.getCompletedTasksReverse());
        }
        return "project/projectHomepage";
    }

    @RequestMapping(value = "publicFeedback")
    @Transactional
    public String getPublicFeedback(Principal principal, Model model)
    {
        model.addAttribute("name", principal.getName());
        model.addAttribute("feedbacks",allFeedbackRepository.findAll());
        return "feedback/publicFeedback";
    }
}
