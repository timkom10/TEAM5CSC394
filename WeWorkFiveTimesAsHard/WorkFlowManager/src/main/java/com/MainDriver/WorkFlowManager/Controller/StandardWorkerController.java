package com.MainDriver.WorkFlowManager.Controller;

import com.MainDriver.WorkFlowManager.Model.Workers.StandardWorker;
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

    public StandardWorkerController(StandardWorkerRepository standardWorkerRepository) {
        this.standardWorkerRepository = standardWorkerRepository;
    }

    @GetMapping("index")
    public  String index(Principal principal, Model model)
    {
        //System.out.println(principal.getName());
        StandardWorker standardWorker = standardWorkerRepository.findByuserName(principal.getName());
        if(standardWorker != null)
        {
            model.addAttribute(standardWorker);
            System.out.println(standardWorker);
        }
        System.out.println("REQUESTING SENT");
        return "StandardWorkers/index";
    }
}
