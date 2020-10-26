package com.driver.workFlowManager.controller;

import com.driver.workFlowManager.model.feedback.Feedback;
import com.driver.workFlowManager.model.projects.Project;
import com.driver.workFlowManager.model.workers.StandardWorker;
import com.driver.workFlowManager.service.FeedbackService;
import com.driver.workFlowManager.service.ManagerService;
import com.driver.workFlowManager.service.ProjectService;
import com.driver.workFlowManager.service.StandardWorkerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    public String portal() {
        return "feedback/FeedbackPortal";
    }

    @GetMapping(value = "composeFeedback")
    public String getComposeFeedback(Principal principal, Model model, String to) {
        usernamePlaceHolder = to;
        model.addAttribute("name", principal.getName());
        model.addAttribute("to", to);
        model.addAttribute("feedback", new Feedback());
        return "feedback/composeFeedback";
    }

    @RequestMapping(value = "feedbackSent", method = RequestMethod.POST)
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
            return "projectHome";
        }
        else if(this.managerService.existsByUsername(principal.getName())) {
            return "projectHomepage";
        }
        return "error";
    }


    @RequestMapping(value = "leaderboard")
    public String getLeaderboard(Model model) {
        List<StandardWorker> standardWorkerList = this.standardWorkerService.getAllStandardWorkersSortedByPoints();
        model.addAttribute("workers", standardWorkerList);
        return "feedback/leaderboard";
    }

    @RequestMapping(value = "publicFeedback")
    public String getPublicFeedback(Principal principal, Model model) {
        model.addAttribute("name", principal.getName());
        model.addAttribute("feedbacks",this.feedbackService.getAllFeedbackSortedByDate());
        return "feedback/publicFeedback";
    }

    @GetMapping(value = "personalFeedback")
    public String getPersonalFeedback(Principal principal,Model model) {
        StandardWorker standardWorker = this.standardWorkerService.getByUsername(principal.getName());
        if(standardWorker != null) {
            model.addAttribute("name", principal.getName());
            model.addAttribute("feedbacks", standardWorker.getPersonalFeedback());
        }
        return "feedback/personalFeedback";
    }

}
