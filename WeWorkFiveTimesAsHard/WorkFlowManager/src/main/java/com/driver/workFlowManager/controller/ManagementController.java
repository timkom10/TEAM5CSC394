package com.driver.workFlowManager.controller;

import com.driver.workFlowManager.model.projects.Milestones;
import com.driver.workFlowManager.model.projects.Project;
import com.driver.workFlowManager.model.projects.Task;
import com.driver.workFlowManager.model.workers.Manager;
import com.driver.workFlowManager.model.workers.StandardWorker;
import com.driver.workFlowManager.repository.ManagerRepository;
import com.driver.workFlowManager.repository.StandardWorkerRepository;
import com.driver.workFlowManager.service.*;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("management")
public class ManagementController {

    private final UserService userService;
    private final AnnouncementService announcementService;
    private final StandardWorkerService standardWorkerService;
    private final ProjectService projectService;
    private final StandardWorkerRepository standardWorkerRepository;
    private final ManagerService managerService;

    public ManagementController(UserService userService, AnnouncementService announcementService, StandardWorkerService standardWorkerService, ProjectService projectService, StandardWorkerRepository standardWorkerRepository, ManagerService managerService, ManagerRepository managerRepository) {
        this.userService = userService;
        this.announcementService = announcementService;
        this.standardWorkerService = standardWorkerService;
        this.projectService = projectService;
        this.standardWorkerRepository = standardWorkerRepository;
        this.managerService = managerService;
    }

    @GetMapping("index")
    public  String index(Principal principal, Model model) {
        model.addAttribute("manager", this.managerService.getByUsername(principal.getName()));
        model.addAttribute("announcements", announcementService.getAllAnnouncementsByUsername(principal.getName()));
        return "management/index";
    }

    @GetMapping("info")
    public String info(Principal principal, Model model) {
        model.addAttribute("workerType", this.managerService.getByUsername(principal.getName()));
        return "Info/info";
    }

    @GetMapping(value = "viewProject")
    @Transactional
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


    @GetMapping(value = "projectInfo")
    @Transactional
    public String getProjectInfo(Principal principal, Model model, Long projectId) {
        Project project = this.projectService.getByID(projectId);
        if(project != null) {
            List<StandardWorker> standardWorkerList = this.standardWorkerService.getAllStandardWorkersSortedByPointsByProject(project);
            model.addAttribute("workers", standardWorkerList);
            model.addAttribute("name", principal.getName());
            model.addAttribute("project", project);
        }
        return "project/projectInfo";
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
    public String getViewSingleTask(Principal principal, Model model, Long projectId, Integer milestoneId, Integer taskId) {
        Task task = this.projectService.getSingleTask(projectId, milestoneId, taskId);
        if(task != null) {
            model.addAttribute("project", this.projectService.getByID(projectId));
            model.addAttribute("ROLE", userService.getByUsername(principal.getName()).getRoles());
            model.addAttribute("name", principal.getName());
            model.addAttribute("task",task);
        }
        return "project/viewSingleTask";
    }

    @GetMapping(value = "markTaskDone")
    @Transactional
    public String getMarkTaskDone(Principal principal, Model model, Long projectId, Integer milestoneId, Integer taskId, String username)
    {
       StandardWorker standardWorker = this.standardWorkerService.getByUsername(username);
       standardWorker.didTask(this.projectService.setTaskDoneReturn(projectId, milestoneId, taskId));
       this.standardWorkerRepository.save(standardWorker);

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
