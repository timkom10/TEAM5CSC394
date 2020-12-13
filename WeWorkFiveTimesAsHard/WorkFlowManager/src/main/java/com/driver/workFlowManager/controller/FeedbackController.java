package com.driver.workFlowManager.controller;

import com.driver.workFlowManager.model.feedback.Feedback;
import com.driver.workFlowManager.model.workers.StandardWorker;
import com.driver.workFlowManager.service.FeedbackService;
import com.driver.workFlowManager.service.StandardWorkerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.security.Principal;

/*
    All standard workers and managers can post and view feedback that they left, or see
    Feedback left for others.
 */
@Controller
@RequestMapping({"management", "standardWorkers"})
public class FeedbackController {
    private final FeedbackService feedbackService;
    private final StandardWorkerService standardWorkerService;

    public FeedbackController(FeedbackService feedbackService, StandardWorkerService standardWorkerService) {
        this.feedbackService = feedbackService;
        this.standardWorkerService = standardWorkerService;
    }

    @GetMapping("feedback")
    public String portal() {
        return "feedback/FeedbackPortal";
    }

    @GetMapping(value = "composeFeedback")
    public String getComposeFeedback(Principal principal, Model model, String to) {
        model.addAttribute("name", principal.getName());
        model.addAttribute("to", to);
        model.addAttribute("feedback", new Feedback());
        return "feedback/composeFeedback";
    }

    @RequestMapping(value = "feedbackSent", method = RequestMethod.POST)
    public String getFeedbackSent(Principal principal,@ModelAttribute("feedback")Feedback feedback, String toUsername) {
        this.feedbackService.addFeedback(feedback,toUsername,principal.getName());
        if(this.standardWorkerService.existsByUsername(principal.getName()))
            return  "redirect:/standardWorkers/viewProject";

        return "redirect:/management/viewProject";
    }

    @GetMapping(value = "leaderboard")
    public String getLeaderboard(Principal principle, Model model) {
        model.addAttribute("name",principle.getName());
        model.addAttribute("workers", this.standardWorkerService.getAllStandardWorkersSortedByPoints());
        return "feedback/leaderboard";
    }

    @GetMapping(value = "publicFeedback")
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
            return "feedback/personalFeedback";
        }
        return "error";
    }
}
