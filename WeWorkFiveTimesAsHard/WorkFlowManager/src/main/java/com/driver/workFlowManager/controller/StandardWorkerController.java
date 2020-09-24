package com.driver.workFlowManager.controller;

import com.driver.workFlowManager.model.projects.Milestones;
import com.driver.workFlowManager.model.projects.Project;
import com.driver.workFlowManager.model.projects.Task;
import com.driver.workFlowManager.model.workers.StandardWorker;
import com.driver.workFlowManager.repository.StandardWorkerRepository;
import com.driver.workFlowManager.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("standardWorkers")
public class StandardWorkerController {

    private final UserService userService;
    private final AnnouncementService announcementService;
    private final StandardWorkerService standardWorkerService;
    private final ProjectService projectService;

    public StandardWorkerController(UserService userService, AnnouncementService announcementService, StandardWorkerService standardWorkerService, ProjectService projectService) {
        this.userService = userService;
        this.announcementService = announcementService;
        this.standardWorkerService = standardWorkerService;
        this.projectService = projectService;
    }

    @GetMapping("index")
    public  String index(Principal principal, Model model)
    {
        model.addAttribute("standardWorker", this.standardWorkerService.getByUsername(principal.getName()));
        model.addAttribute("announcements", this.announcementService.getAllAnnouncementsByUsername(principal.getName()));
        return "standardWorkers/index";
    }


    @GetMapping("info")
    public String info(Principal principal, Model model) {
        model.addAttribute("workerType", this.standardWorkerService.getByUsername(principal.getName()));
        return "Info/info";
    }

    @GetMapping(value = "projectInfo")
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