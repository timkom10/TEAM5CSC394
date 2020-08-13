package com.MainDriver.WorkFlowManager.controller;

import com.MainDriver.WorkFlowManager.model.projects.Milestones;
import com.MainDriver.WorkFlowManager.model.projects.Project;
import com.MainDriver.WorkFlowManager.model.projects.Task;
import com.MainDriver.WorkFlowManager.model.workers.Manager;
import com.MainDriver.WorkFlowManager.model.workers.StandardWorker;
import com.MainDriver.WorkFlowManager.repository.ManagerRepository;
import com.MainDriver.WorkFlowManager.repository.StandardWorkerRepository;
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
    private UserService userService;

    @Autowired
    AnnouncementService announcementService;

    @Autowired
    private StandardWorkerService standardWorkerService;

    @Autowired
    ProjectService projectService;

    @Autowired
    StandardWorkerRepository standardWorkerRepository;

    private final ManagerRepository managerRepository;

    public ManagementController(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    @GetMapping("index")
    @Transactional
    public  String index(Principal principal, Model model)
    {
        Manager manager =  this.managerRepository.findByUserName(principal.getName());
        if(manager != null) {
            model.addAttribute("manager", managerRepository.findByUserName(principal.getName()));
            model.addAttribute("announcements", announcementService.getAllAnnouncementsByUsername(principal.getName()));
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
