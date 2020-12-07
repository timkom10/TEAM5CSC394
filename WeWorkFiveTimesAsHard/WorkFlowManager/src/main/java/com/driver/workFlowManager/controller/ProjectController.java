package com.driver.workFlowManager.controller;

import com.driver.workFlowManager.model.projects.Milestones;
import com.driver.workFlowManager.model.projects.Project;
import com.driver.workFlowManager.model.projects.Task;
import com.driver.workFlowManager.model.workers.StandardWorker;
import com.driver.workFlowManager.model.workers.Users;
import com.driver.workFlowManager.service.ProjectService;
import com.driver.workFlowManager.service.StandardWorkerService;
import com.driver.workFlowManager.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping({"standardWorkers", "management"})
public class ProjectController {

    private final UserService userService;
    private final ProjectService projectService;
    private final StandardWorkerService standardWorkerService;

    public ProjectController(UserService userService, ProjectService projectService, StandardWorkerService standardWorkerService) {
        this.userService = userService;
        this.projectService = projectService;
        this.standardWorkerService = standardWorkerService;
    }

    @GetMapping(value = "viewProject")
    public String getViewProject(Principal principal,Model model) {
        Project project = this.projectService.getProjectByUsername(principal.getName());
        if(project != null) {
            model.addAttribute("ROLE", userService.getByUsername(principal.getName()).getRoles());
            model.addAttribute("name", principal.getName());
            model.addAttribute("workers", project.getTeamMembers());
            model.addAttribute("project", project);
            model.addAttribute("milestones", project.getMilestones());
            model.addAttribute("completedTasks", project.getCompletedTasksReverse());
            return "project/projectHome";
        }

        /*No project, find user type*/
        Users user = this.userService.getByUsername(principal.getName());
        if(user != null) {
            if(user.getRoles().equals("STANDARDWORKER")) {
                /*Case where no project, is standard worker*/
                model.addAttribute("name", principal.getName());
                return "project/noProjectStandardWorker";
            }
            else if(user.getRoles().equals("MANAGER")) {
                /*Case where no project, is Manager*/
                model.addAttribute("project", new Project());
                model.addAttribute("name", principal.getName());
                return "project/makeProjectInitial";
            }
        }
        /*Unknown Type*/
        return "error";
    }

    @GetMapping(value = "projectInfo")
    public String getProjectInfo(Principal principal, Model model, Long projectId) {
        Project project = this.projectService.getByID(projectId);
        if(project != null) {
            List<StandardWorker> standardWorkerList = this.standardWorkerService.getAllStandardWorkersSortedByPointsByProject(project);
            model.addAttribute("workers", standardWorkerList);
            model.addAttribute("name", principal.getName());
            model.addAttribute("project", project);
            return "project/projectInfo";
        }
        return "error";
    }

    @GetMapping(value = "viewMilestone")
    public String getViewMilestone(Principal principal,Model model, Long projectId, Integer milestoneId) {
        Milestones milestones = this.projectService.getMilestone(projectId, milestoneId);
        if(milestones != null) {
            List<Task> tasks = this.projectService.getTasksByMileStoneId(projectId, milestoneId);
            model.addAttribute("project",this.projectService.getByID(projectId));
            model.addAttribute("name", principal.getName());
            model.addAttribute("ROLE", userService.getByUsername(principal.getName()).getRoles());
            model.addAttribute("milestone", milestones);
            model.addAttribute("tasks", tasks);
            return "project/milestoneView";
        }
        return "error";
    }

    @GetMapping(value = "viewSingleTask")
    public String getViewSingleTask(Principal principal, Model model, Long projectId, Integer milestoneId, Integer taskId) {
        Task task = this.projectService.getSingleTask(projectId, milestoneId, taskId);
        if(task != null) {
            model.addAttribute("project", this.projectService.getByID(projectId));
            model.addAttribute("ROLE", userService.getByUsername(principal.getName()).getRoles());
            model.addAttribute("name", principal.getName());
            model.addAttribute("task",task);
            return "project/viewSingleTask";
        }
        return "error";
    }

    @GetMapping(value = "volunteerTask")
    public String getVolunteerTask(Principal principal, Model model, Long projectId, Integer milestoneId, Integer taskId) {
        this.projectService.setTaskToUser(principal.getName(), projectId, milestoneId, taskId);
        return getViewMilestone(principal, model, projectId, milestoneId);
    }

    @GetMapping(value = "standardWorkerMarkTaskComplete")
    public String getStandardWorkerTaskComplete(Principal principal, Model model, Long projectId, Integer milestoneId, Integer taskId) {
        this.projectService.setTaskUpForReview(projectId, milestoneId, taskId);
        return getViewMilestone(principal, model, projectId, milestoneId);
    }

    @GetMapping(value = "managerMarkTaskDone")
    public String getManagerMarkTaskDone(Principal principal, Model model, Long projectId, Integer milestoneId, Integer taskId, String username) {
        this.standardWorkerService.markTaskComplete(username, this.projectService.setTaskDoneReturn(projectId, milestoneId, taskId));
        return getViewMilestone(principal, model, projectId, milestoneId);
    }

    @GetMapping(value = "viewUsersTasks")
    public String getViewUsersTasks(Principal principal, Model model, Long projectId, Integer milestoneId) {
        List<Task> tasks = this.projectService.getTasksByUsernameProjectIdAndMilestoneId(principal.getName(),projectId, milestoneId);
        if(tasks != null) {
            model.addAttribute("project", this.projectService.getByID(projectId));
            model.addAttribute("milestone", this.projectService.getMilestone(projectId, milestoneId));
            model.addAttribute("name", principal.getName());
            model.addAttribute("tasks",tasks);
            return "project/viewUsersTasks";
        }
        return "error";
    }

    @GetMapping(value = "makeProject")
    public String getMakeProject(Principal principal, Model model, @ModelAttribute("project") Project project) {
        this.projectService.bindProjectToManager(project, principal.getName());
        return "error";
    }
}
