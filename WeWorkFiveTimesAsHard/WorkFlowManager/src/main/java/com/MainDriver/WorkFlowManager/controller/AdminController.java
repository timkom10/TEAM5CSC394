package com.MainDriver.WorkFlowManager.controller;

import com.MainDriver.WorkFlowManager.model.Users;
import com.MainDriver.WorkFlowManager.model.workers.Admin;
import com.MainDriver.WorkFlowManager.model.workers.StandardWorker;
import com.MainDriver.WorkFlowManager.service.AdminService;
import com.MainDriver.WorkFlowManager.service.StandardWorkerService;
import com.MainDriver.WorkFlowManager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    UserService userService;

    @Autowired
    StandardWorkerService standardWorkerService;

    private static  String usernamePlaceholder ="";

    @GetMapping("index")
    public String index(Principal principal, Model model) {
        Admin admin = adminService.findByUserName(principal.getName());
        model.addAttribute("workerType", admin);
        return "admin/index";
    }

    @GetMapping("info")
    public String info(Principal principal, Model model) {

        Admin admin = adminService.findByUserName(principal.getName());
        model.addAttribute("workerType", admin);
        return "Info/info";
    }

    @RequestMapping("Hr")
    public String getHR() {
        return "admin/HumanResources";
    }

    @GetMapping("feedback")
    public String portal() {
        return "feedback/FeedbackPortal";
    }

    @RequestMapping(value = "RemoveUser", method = RequestMethod.GET)
    public String getUserSearch(Model model, @RequestParam(defaultValue = "") String username) {
        model.addAttribute("users", userService.findByUsername(username));
        return "admin/RemoveUser";
    }


    @RequestMapping(value = "deleteUser", method = RequestMethod.GET)
    public String getRemoveUser(Model model, String username) {
        userService.removeUser(username);
        model.addAttribute("users", userService.findByUsername(""));
        return "admin/RemoveUser";
    }

    @RequestMapping("addUser")
    public String getAddUser(Model model) {
        model.addAttribute("user", new Users());
        return "admin/addUser";
    }

    @RequestMapping(value = "insertUser", method = RequestMethod.POST)
    public String getInsert(@ModelAttribute("user") Users user, Principal principal, Model model)
    {
        if(userService.addUser(user))
        {
            if(user.getRoles().equals("STANDARDWORKER")) {
                //make the user a standard worker
                usernamePlaceholder = user.getUsername();
                model.addAttribute("worker", new StandardWorker());
                return "admin/addStandardWorker";
            }
            else {
                //make the user a manager
                System.out.println("ROLES NOT EQUAL:  " +user.getRoles());
                System.out.println(user.getRoles());
            }
        }
        return "admin/addUser";
    }

    @RequestMapping(value = "insertStandardWorker", method = RequestMethod.POST)
    public String getInsertStandardWorker(@ModelAttribute("worker") StandardWorker standardWorker, BindingResult bindingResult, Principal principal, Model model)
    {

        Users user = userService.getByUsername(this.usernamePlaceholder);
        if(user != null)
        {

            standardWorker.setROLE(user.getRoles());
            standardWorker.setUserName(user.getUsername());
            standardWorkerService.addStandardWorker(standardWorker);
        }
        model.addAttribute("user", new Users());
       return "admin/addUser";
    }

}
