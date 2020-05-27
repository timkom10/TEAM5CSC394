package com.MainDriver.WorkFlowManager.Controller;

import com.MainDriver.WorkFlowManager.Model.Workers.StandardWorker;
import com.MainDriver.WorkFlowManager.repository.AnnouncementRepository;
import com.MainDriver.WorkFlowManager.repository.StandardWorkerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("StandardWorkers")
public class StandardWorkerController
{
    private final StandardWorkerRepository standardWorkerRepository;
    private final AnnouncementRepository announcementRepository;

    public StandardWorkerController(StandardWorkerRepository standardWorkerRepository, AnnouncementRepository announcementRepository) {
        this.standardWorkerRepository = standardWorkerRepository;
        this.announcementRepository = announcementRepository;
    }

    @GetMapping("index")
    public  String index(Principal principal, Model model)
    {
        StandardWorker standardWorker = standardWorkerRepository.findByuserName(principal.getName());
        if(standardWorker != null)
        {
            model.addAttribute(standardWorker);
            System.out.println(standardWorker);
        }
        return "StandardWorkers/index";
    }

    @GetMapping("feedbackPortal")
    public String portal()
    {
        return "feedback/FeedbackPortal";
    }
}
