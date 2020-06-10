package com.MainDriver.WorkFlowManager.controller;

import com.MainDriver.WorkFlowManager.model.feedback.Feedback;
import com.MainDriver.WorkFlowManager.model.messaging.Announcement;
import com.MainDriver.WorkFlowManager.model.messaging.Message;
import com.MainDriver.WorkFlowManager.model.projects.Milestones;
import com.MainDriver.WorkFlowManager.model.projects.Project;
import com.MainDriver.WorkFlowManager.model.projects.Task;
import com.MainDriver.WorkFlowManager.model.workers.Manager;
import com.MainDriver.WorkFlowManager.model.workers.StandardWorker;
import com.MainDriver.WorkFlowManager.repository.ManagerRepository;
import com.MainDriver.WorkFlowManager.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("management")
public class ManagementController {

    @Autowired
    MessagingService messagingService;

    @Autowired
    private UserService userService;

    @Autowired
    private StandardWorkerService standardWorkerService;

    @Autowired
    AnnouncementService announcementService;

    @Autowired
    FeedbackService feedbackService;

    @Autowired
    ProjectService projectService;

    static String usernamePlaceHolder ="";

    private final ManagerRepository managerRepository;

    public ManagementController(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    @GetMapping("index")
    @Transactional
    public  String index(Principal principal, Model model)
    {
        Manager manager =  this.managerRepository.findByUserName(principal.getName());
        if(manager != null)
        {
            model.addAttribute("manager", managerRepository.findByUserName(principal.getName()));
            model.addAttribute("announcements", manager.getAnnouncements());
        }
        return "management/index";
    }

    @GetMapping("info")
    public String info(Principal principal, Model model) {
        Manager manager = managerRepository.findByUserName(principal.getName());
        if(manager != null) {
            model.addAttribute("workerType", manager);
        }
        return "Info/info";
    }

    @GetMapping("feedback")
    public String portal() {
        return "feedback/FeedbackPortal";
    }

    @GetMapping("messagingPortal")
    public String getMessagingPortal() {
        return "messaging/messagingPortal";
    }

    @GetMapping("searchUserToMessage")
    public String getSearchUserToMessage(Principal principal, Model model, @RequestParam(defaultValue = "") String username) {
        model.addAttribute("users",userService.findByUsername(username));
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
        Manager manager = managerRepository.findByUserName(principal.getName());
        if(manager != null) {
            model.addAttribute("manager", manager);
            model.addAttribute("announcements", manager.getAnnouncements());
        }
        return "management/index";
    }

    @GetMapping(value = "composeAnnouncement")
    public String getComposeAnnouncement(Principal principal,Model model) {

        model.addAttribute("name", principal.getName());
        model.addAttribute("announcement", new Announcement());
        return "announcements/composeAnnouncement";
    }

    @RequestMapping(value = "sendAnnouncement", method = RequestMethod.POST)
    public String getSendAnnouncement(Principal principal,Model model, @ModelAttribute("announcement")Announcement announcement) {
        this.announcementService.sendAnnouncement(announcement, principal.getName(), principal.getName());

        Manager manager = managerRepository.findByUserName(principal.getName());
        if (manager != null) {
            model.addAttribute("manager", manager);
            model.addAttribute("announcements", manager.getAnnouncements());
        }
        return "management/index";
    }

    @RequestMapping(value = "leaderboard")
    @Transactional
    public String getLeaderboard(Principal principal, Model model) {
        List<StandardWorker> standardWorkerList = this.standardWorkerService.getAllStandardWorkersSortedByPoints();
        model.addAttribute("workers", standardWorkerList);
        return "feedback/leaderboard";
    }

    @GetMapping(value = "composeFeedback")
    @Transactional
    public String getComposeFeedback(Principal principal,Model model, String to) {
        usernamePlaceHolder = to;
        model.addAttribute("name", principal.getName());
        model.addAttribute("to", to);
        model.addAttribute("feedback", new Feedback());
        return "feedback/composeFeedback";
    }


    @RequestMapping(value = "feedbackSent", method = RequestMethod.POST)
    @Transactional
    public String getFeedbackSent(Principal principal,Model model, @ModelAttribute("feedback")Feedback feedback) {
        this.feedbackService.addFeedback(feedback,usernamePlaceHolder,principal.getName());
        Project project = this.projectService.getProjectByUsername(principal.getName());

        if(project != null) {
            model.addAttribute("name", principal.getName());
            model.addAttribute("workers", project.getTeamMembers());
            model.addAttribute("project", project);
            model.addAttribute("milestones", project.getMilestones());
            model.addAttribute("completedTasks", project.getCompletedTasksReverse());
        }
        return "project/managerViewProject";
    }

    @GetMapping(value = "viewProject")
    public String getViewProject(Principal principal,Model model)
    {
        Project project = this.projectService.getProjectByUsername(principal.getName());
        if(project != null) {
            model.addAttribute("name", principal.getName());
            model.addAttribute("workers", project.getTeamMembers());
            model.addAttribute("project", project);
            model.addAttribute("milestones", project.getMilestones());
            model.addAttribute("completedTasks", project.getCompletedTasksReverse());
        }
        return "project/managerViewProject";
    }

    @GetMapping(value = "viewMilestone")
    @Transactional
    public String getViewMilestone(Principal principal,Model model, Long projectId, Integer milestoneId) {
        Milestones milestones = this.projectService.getMilestone(projectId, milestoneId);
        List<Task> tasks = this.projectService.getTasksByMileStoneId(projectId, milestoneId);
        if(milestones != null) {
            model.addAttribute("project",this.projectService.getByID(projectId));
            model.addAttribute("name", principal.getName());
            model.addAttribute("ROLE", userService.getByUsername(principal.getName()).getRoles());
            model.addAttribute("milestone", milestones);
            model.addAttribute("tasks", tasks);
        }
        return "project/milestoneView";
    }

    @GetMapping(value = "viewSingleTask")
    @Transactional
    public String getViewSingleTask(Principal principal, Model model, Long projectId, Integer milestoneId, Integer taskId)
    {
        Task task = this.projectService.getSingleTask(projectId, milestoneId, taskId);
        if(task != null) {
            model.addAttribute("ROLE", userService.getByUsername(principal.getName()).getRoles());
            model.addAttribute("name", principal.getName());
            model.addAttribute("task",task);
        }
        return "project/viewSingleTask";
    }
}
