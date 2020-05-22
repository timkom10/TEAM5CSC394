package com.MainDriver.WorkFlowManager.Controller;

import com.MainDriver.WorkFlowManager.repository.WorkerTypeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StandardWorkerController {

    private final WorkerTypeRepository workerTypeRepository;

    public StandardWorkerController(WorkerTypeRepository workerTypeRepository) {
        this.workerTypeRepository = workerTypeRepository;
    }

    @RequestMapping("/StandardWorkers")
    public String getStandardWorkers(Model model)
    {
        model.addAttribute("standardWorkers", workerTypeRepository.findAll());
        return "StandardWorkersView/StandardWorkerHomePage";
    }
}
