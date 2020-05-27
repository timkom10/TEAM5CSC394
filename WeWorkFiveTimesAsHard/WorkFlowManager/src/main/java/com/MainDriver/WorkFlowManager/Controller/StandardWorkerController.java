package com.MainDriver.WorkFlowManager.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("StandardWorkers")
public class StandardWorkerController {

    @GetMapping("index")
    public  String index()
    {
        return "StandardWorkers/index";
    }
}
