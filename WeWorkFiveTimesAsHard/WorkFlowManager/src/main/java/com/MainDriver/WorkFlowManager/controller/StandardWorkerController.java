package com.MainDriver.WorkFlowManager.controller;

import com.MainDriver.WorkFlowManager.model.feedback.Feedback;
import com.MainDriver.WorkFlowManager.model.projects.Milestones;
import com.MainDriver.WorkFlowManager.model.projects.Project;
import com.MainDriver.WorkFlowManager.model.projects.Task;
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
import java.util.List;

@Controller
@RequestMapping("standardWorkers")
public class StandardWorkerController
{

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

    @Autowired
    ProjectService projectService;

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
            model.addAttribute("announcements", announcementService.getAllAnnouncementsByUsername(principal.getName()));
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

    @GetMapping(value = "projectInfo")
    @Transactional
    public String getProjectInfo(Principal principal, Model model, Long projectId) {
        Project project = this.projectService.getByID(projectId);
        List<StandardWorker> standardWorkerList = this.standardWorkerService.getAllStandardWorkersSortedByPointsByProject(project);
        if(project != null) {
            model.addAttribute("workers", standardWorkerList);
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
        return "project/projectHomepage";
    }

    @RequestMapping(value = "publicFeedback")
    @Transactional
    public String getPublicFeedback(Principal principal, Model model) {
        model.addAttribute("name", principal.getName());
        model.addAttribute("feedbacks",allFeedbackRepository.findAll());
        return "feedback/publicFeedback";
    }

    @RequestMapping(value = "leaderboard")
    @Transactional
    public String getLeaderboard(Principal principal, Model model) {
        List<StandardWorker> standardWorkerList = this.standardWorkerService.getAllStandardWorkersSortedByPoints();

        model.addAttribute("workers", standardWorkerList);
        return "feedback/leaderboard";
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
        return "project/projectHomepage";
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

    @GetMapping(value = "viewUsersTasks")
    @Transactional
    public String getViewUsersTasks(Principal principal, Model model, Long projectId, Integer milestoneId) {
        List<Task> tasks = this.projectService.getTasksByUsernameProjectIdAndMilestoneId(principal.getName(),projectId, milestoneId);
        if(tasks != null) {
            model.addAttribute("project", this.projectService.getByID(projectId));
            model.addAttribute("milestone", this.projectService.getMilestone(projectId, milestoneId));
            model.addAttribute("name", principal.getName());
            model.addAttribute("tasks",tasks);
        }
        return "project/viewUsersTasks";
    }

    @GetMapping(value = "viewSingleTask")
    @Transactional
    public String getViewSingleTask(Principal principal, Model model, Long projectId, Integer milestoneId, Integer taskId)
    {
        Task task = this.projectService.getSingleTask(projectId, milestoneId, taskId);
        if(task != null) {
            model.addAttribute("project", this.projectService.getByID(projectId));
            model.addAttribute("ROLE", userService.getByUsername(principal.getName()).getRoles());
            model.addAttribute("name", principal.getName());
            model.addAttribute("task",task);
        }
        return "project/viewSingleTask";
    }

    @GetMapping(value = "addTask")
    @Transactional
    public String getAddTask(Principal principal, Model model, Long projectId, Integer milestoneId, Integer taskId)
    {
        this.projectService.setTaskToUser(principal.getName(), projectId, milestoneId, taskId);
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

    @GetMapping(value = "markTaskComplete")
    @Transactional
    public String getMarkTaskComplete(Principal principal, Model model, Long projectId, Integer milestoneId, Integer taskId)
    {
        this.projectService.setTaskUpForReview(projectId, milestoneId, taskId);
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
}
