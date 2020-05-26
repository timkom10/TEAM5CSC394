package com.MainDriver.WorkFlowManager.Controller;

import com.MainDriver.WorkFlowManager.repository.AnnouncementRepository;
import com.MainDriver.WorkFlowManager.repository.WorkerTypeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StandardWorkerController {

    private final WorkerTypeRepository workerTypeRepository;
    private final AnnouncementRepository announcementRepository;

    public StandardWorkerController(WorkerTypeRepository workerTypeRepository,
                                    AnnouncementRepository announcementRepository
    )
    {
        this.workerTypeRepository = workerTypeRepository;
        this.announcementRepository = announcementRepository;
    }

    @RequestMapping("/StandardWorkers")
    public String getStandardWorkers(Model model)
    {
        model.addAttribute("standardWorkers", workerTypeRepository.findAll());
        model.addAttribute("announcements", announcementRepository.findAll());
        return "StandardWorkersView/StandardWorkerHomePage";
    }
}
