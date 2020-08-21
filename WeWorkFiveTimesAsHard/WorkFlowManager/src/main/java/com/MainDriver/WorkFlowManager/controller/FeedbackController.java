package com.MainDriver.WorkFlowManager.controller;

import com.MainDriver.WorkFlowManager.model.feedback.Feedback;
import com.MainDriver.WorkFlowManager.model.projects.Project;
import com.MainDriver.WorkFlowManager.model.workers.StandardWorker;
import com.MainDriver.WorkFlowManager.service.FeedbackService;
import com.MainDriver.WorkFlowManager.service.ManagerService;
import com.MainDriver.WorkFlowManager.service.ProjectService;
import com.MainDriver.WorkFlowManager.service.StandardWorkerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;

/*
    All standard workers and managers can post and view feedback that they left, or see
    Feedback left for others.
 */
@Controller
@RequestMapping({"standardWorkers", "management"})
public class FeedbackController {

    private final ManagerService managerService;
    private final ProjectService projectService;
    private final FeedbackService feedbackService;
    private final StandardWorkerService standardWorkerService;
    private static String usernamePlaceHolder = "";

    public FeedbackController(ManagerService managerService, ProjectService projectService, FeedbackService feedbackService, StandardWorkerService standardWorkerService) {
        this.managerService = managerService;
        this.projectService = projectService;
        this.feedbackService = feedbackService;
        this.standardWorkerService = standardWorkerService;
    }


    @GetMapping("feedback")
    @Transactional
    public String portal() {
        return "feedback/FeedbackPortal";
    }

    @GetMapping(value = "composeFeedback")
    @Transactional
    public String getComposeFeedback(Principal principal, Model model, String to) {
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

        /* Can either be a manager or a standard worker*/
        if(this.standardWorkerService.existsByUsername(principal.getName())) {
            return "project/projectHomepage";
        }
        else if(this.managerService.existsByUsername(principal.getName())) {
            return "project/managerViewProject";
        }
        return "error";
    }


    @RequestMapping(value = "leaderboard")
    @Transactional
    public String getLeaderboard(Model model) {
        List<StandardWorker> standardWorkerList = this.standardWorkerService.getAllStandardWorkersSortedByPoints();
        model.addAttribute("workers", standardWorkerList);
        return "feedback/leaderboard";
    }

    @RequestMapping(value = "publicFeedback")
    @Transactional
    public String getPublicFeedback(Principal principal, Model model) {
        model.addAttribute("name", principal.getName());
        model.addAttribute("feedbacks",this.feedbackService.getAllFeedbackSortedByDate());
        return "feedback/publicFeedback";
    }

}
