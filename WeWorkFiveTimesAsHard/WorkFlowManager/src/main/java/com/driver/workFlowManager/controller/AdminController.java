package com.driver.workFlowManager.controller;

import com.driver.workFlowManager.model.workers.Users;
import com.driver.workFlowManager.model.workers.Admin;
import com.driver.workFlowManager.model.workers.Manager;
import com.driver.workFlowManager.model.workers.StandardWorker;
import com.driver.workFlowManager.service.implementation.AdminServiceImp;
import com.driver.workFlowManager.service.implementation.ManagerServiceImp;
import com.driver.workFlowManager.service.implementation.StandardWorkerServiceImp;
import com.driver.workFlowManager.service.implementation.UserServiceImp;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.security.Principal;

@Controller
@RequestMapping("admin")
public class AdminController {

    private final AdminServiceImp adminService;
    private final UserServiceImp userService;
    private final ManagerServiceImp managerService;
    private final StandardWorkerServiceImp standardWorkerService;
    private static String usernamePlaceholder ="";

    public AdminController(AdminServiceImp adminService, UserServiceImp userService, ManagerServiceImp managerService, StandardWorkerServiceImp standardWorkerService) {
        this.adminService = adminService;
        this.userService = userService;
        this.managerService = managerService;
        this.standardWorkerService = standardWorkerService;
    }

    @GetMapping("index")
    public String index(Principal principal, Model model) {
        Admin admin = adminService.findByUserName(principal.getName());
        if(admin != null)
        {
            model.addAttribute("admin", admin);
            model.addAttribute("announcements", admin.getAnnouncements());
        }
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
    public String getInsert(@ModelAttribute("user") Users user, Principal principal, Model model) {
        if(userService.addUser(user)) {
            usernamePlaceholder = user.getUsername();
            if(user.getRoles().equals("STANDARDWORKER")) {
                model.addAttribute("worker", new StandardWorker());
                return "admin/addStandardWorker";
            }
            else if(user.getRoles().equals("MANAGER")) {
                model.addAttribute("manager", new Manager());
                return "admin/addManager";
            }
        }
        return "admin/addUser";
    }

    @RequestMapping(value = "insertStandardWorker", method = RequestMethod.POST)
    public String getInsertStandardWorker(@ModelAttribute("worker") StandardWorker standardWorker,Model model) {
        standardWorkerService.addStandardWorker(userService.getByUsername(this.usernamePlaceholder), standardWorker);
        model.addAttribute("user", new Users());
        return "admin/addUser";
    }

    @RequestMapping(value = "insertManager", method = RequestMethod.POST)
    public String getInsertManager(@ModelAttribute("manager") Manager manager,Model model) {
        managerService.addManager(userService.getByUsername(this.usernamePlaceholder), manager);
        model.addAttribute("user", new Users());
        return "admin/addUser";
    }

    @RequestMapping(value = "searchTeamMember", method = RequestMethod.GET)
    public String getTeamMemberSearch(Model model, @RequestParam(defaultValue = "") String username) {
        model.addAttribute("workers", standardWorkerService.findAllByUsername(username));
        return "admin/searchTeamMembers";
    }

    @GetMapping("alterTeamMember")
    public String getAlterEmployee(Model model, String username)
    {
        this.usernamePlaceholder = username;
        model.addAttribute("worker", standardWorkerService.getByUsername(username));
        return "admin/alterTeamMember";
    }

    @RequestMapping(value = "insertAlteredStandardWorker", method = RequestMethod.POST)
    public String getInsertAlteredStandardWorker(@ModelAttribute("worker") StandardWorker standardWorker,Model model)
    {
        standardWorkerService.insertAlteredStandardWorker(standardWorker, usernamePlaceholder);
        model.addAttribute("user", new Users());
        return "admin/addUser";
    }

}
