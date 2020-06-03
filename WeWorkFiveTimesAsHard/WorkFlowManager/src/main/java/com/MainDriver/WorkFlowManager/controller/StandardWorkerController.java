package com.MainDriver.WorkFlowManager.controller;

import com.MainDriver.WorkFlowManager.model.workers.StandardWorker;
import com.MainDriver.WorkFlowManager.repository.AnnouncementRepository;
import com.MainDriver.WorkFlowManager.repository.StandardWorkerRepository;
import com.MainDriver.WorkFlowManager.service.StandardWorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
@RequestMapping("StandardWorkers")
public class StandardWorkerController
{
    @Autowired
    private StandardWorkerService standardWorkerService;

    private final StandardWorkerRepository standardWorkerRepository;

    public StandardWorkerController(StandardWorkerRepository standardWorkerRepository) {
        this.standardWorkerRepository = standardWorkerRepository;
    }

    @GetMapping("index")
    public  String index(Principal principal, Model model)
    {
        StandardWorker standardWorker = standardWorkerRepository.findByuserName(principal.getName());
        if(standardWorker != null) {
            model.addAttribute(standardWorker);
            model.addAttribute("announcements",standardWorkerService.getAllAnnouncements(standardWorker));
        }
        return "StandardWorkers/index";
    }

    @GetMapping("feedback")
    public String portal() {
        return "feedback/FeedbackPortal";
    }

    @GetMapping("info")
    public String info(Principal principal, Model model) {
        StandardWorker standardWorker = standardWorkerRepository.findByuserName(principal.getName());
        if(standardWorker != null) {
            model.addAttribute("workerType", standardWorker);
        }
        return "Info/info";
    }


    @RequestMapping(value = "removeAnnouncement", method = RequestMethod.GET)
    public String getRemoveAnnouncement(Model model, Principal principal, Long announcementId) {

        StandardWorker standardWorker = standardWorkerRepository.findByuserName(principal.getName());
        if(standardWorker != null) {
            System.out.println("Attempting to remove SW: " + standardWorker.getId() + " with announce: " + announcementId);
            standardWorkerService.removeAnnouncement(standardWorker, announcementId);
            model.addAttribute(standardWorker);
            model.addAttribute("announcements",standardWorkerService.getAllAnnouncements(standardWorker));
        }
        return "StandardWorkers/index";
    }
}
