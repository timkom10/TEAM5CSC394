package com.driver.workFlowManager.controller;

import com.driver.workFlowManager.model.workers.Users;
import com.driver.workFlowManager.model.workers.Admin;
import com.driver.workFlowManager.model.workers.Manager;
import com.driver.workFlowManager.model.workers.StandardWorker;
import com.driver.workFlowManager.service.ManagerService;
import com.driver.workFlowManager.service.StandardWorkerService;
import com.driver.workFlowManager.service.implementation.AdminServiceImp;
import com.driver.workFlowManager.service.implementation.UserServiceImp;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@Controller
@RequestMapping("admin")
public class AdminController {

    private final AdminServiceImp adminService;
    private final UserServiceImp userService;
    private final ManagerService managerService;
    private final StandardWorkerService standardWorkerService;

    public AdminController(AdminServiceImp adminService, UserServiceImp userService, ManagerService managerService, StandardWorkerService standardWorkerService) {
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
        model.addAttribute("workerType", adminService.findByUserName(principal.getName()));
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
    public String getAddUser() {
        return "admin/selectUserTypeToAdd";
    }

    @RequestMapping(value = "searchTeamMember", method = RequestMethod.GET)
    public String getTeamMemberSearch(Model model, @RequestParam(defaultValue = "") String username) {
        model.addAttribute("users", userService.findByUsername(username));
        return "admin/searchTeamMembers";
    }

    @RequestMapping("addStandardWorker")
    public String getAddStandardWorker(Model model) {
        model.addAttribute("user", new Users());
        model.addAttribute("worker", new StandardWorker());
        return "admin/addStandardWorker";
    }

    @RequestMapping(value = "makeStandardWorker", method = RequestMethod.POST)
    public String getMakeStandardWorker(@ModelAttribute("user") Users user, @ModelAttribute("worker") StandardWorker standardWorker, Model model) {
        this.adminService.addStandardWorker(user, standardWorker);
        model.addAttribute("currentSW", standardWorker.getUserName());
        model.addAttribute("managers", this.managerService.findManagersByUsernameLike(""));
        return "admin/addStandardWorkerSelectManager";
    }

    @RequestMapping(value = "makeStandardWorkerSelectManager", method = RequestMethod.GET)
    public String getMakeStandardWorkerSelectManager(Model model, @RequestParam(defaultValue = "") String username, String standardWorkerUsername) {
        model.addAttribute("currentSW", standardWorkerUsername);
        model.addAttribute("managers", this.managerService.findManagersByUsernameLike(username));
        return "admin/addStandardWorkerSelectManager";
    }

    @RequestMapping(value = "insertStandardWorker", method = RequestMethod.GET)
    public String getInsertStandardWorker(String standardWorkerUsername, String managerUsername) {
        this.adminService.bindStandardWorkerAndManager(standardWorkerUsername, managerUsername);
        return "redirect:/admin/index";
    }

    @RequestMapping("addManager")
    public String getAddManager(Model model) {
        model.addAttribute("user", new Users());
        model.addAttribute("manager", new Manager());
        return "admin/addManager";
    }

    @RequestMapping("makeManager")
    public String getMakeManagerSelectTeamMembers(@ModelAttribute("user") Users user, @ModelAttribute("manager") Manager manager, Model model) {
        this.adminService.addManager(user, manager);
        model.addAttribute("currentM", user.getUsername());
        model.addAttribute("workers", this.standardWorkerService.getAllFreeWorkersByUsername(""));
        return "admin/addManagerSelectTeamMembers";
    }

    @RequestMapping("makeManagerSelectTeamMembers")
    public String getMakeManagersSelectTeamMembers(Model model, @RequestParam(defaultValue = "") String username, String managerUsername) {
        model.addAttribute("currentM", managerUsername);
        model.addAttribute("workers", this.standardWorkerService.getAllFreeWorkersByUsername(username));
        return "admin/addManagerSelectTeamMembers";
    }

    @RequestMapping("addTeamMemberToManager")
    public String getAddTeamMemberToManager(Model model, String workerUsername, String managerUsername) {
        this.adminService.bindStandardWorkerAndManager(workerUsername, managerUsername);
        model.addAttribute("currentM", managerUsername);
        model.addAttribute("workers", this.standardWorkerService.getAllFreeWorkersByUsername(""));
        return "admin/addManagerSelectTeamMembers";
    }

    @RequestMapping("addManagerRemoveTeamMember")
    public String getAddManagerRemoveTeamMember(Model model, String managerUsername) {
        model.addAttribute("currentM", managerUsername);
        model.addAttribute("workers", this.standardWorkerService.getAllStandardWorkerByManager(this.managerService.getByUsername(managerUsername)));
        return "admin/addManagerRemoveTeamMembers";
    }

    @RequestMapping("removeTeamMemberFromManager")
    public String getRemoveTeamMemberFromManager(Model model, String managerUsername, String workerUsername) {
        this.managerService.removeWorkerFromManager(workerUsername, managerUsername);
        model.addAttribute("currentM", managerUsername);
        model.addAttribute("workers", this.standardWorkerService.getAllStandardWorkerByManager(this.managerService.getByUsername(managerUsername)));
        return "admin/addManagerRemoveTeamMembers";
    }

    @RequestMapping("addAdmin")
    public String getAddAdmin(Model model) {
        model.addAttribute("user", new Users());
        model.addAttribute("admin", new Admin());
        return "admin/addAdmin";
    }

    @RequestMapping("makeAdmin")
    public String getMakeAdmin(@ModelAttribute("user") Users user, @ModelAttribute("admin") Admin admin, Principal principal) {
        this.adminService.addAdmin(user, admin);
        if(principal.getName().equals(user.getUsername())) {
            return "login";
        }
        return "redirect:/admin/index";
    }

    @GetMapping("alterWorker")
    public String getAlterWorker(Model model,String username)
    {
        /*Return the original standard worker from service*/
        Users user = this.userService.getByUsername(username);
        switch (user.getRoles()) {
            case "STANDARDWORKER":
                model.addAttribute("user", user);
                model.addAttribute("worker", this.adminService.removeStandardWorkerAndReturn(username));
                return "admin/addStandardWorker";
            case "MANAGER":
                model.addAttribute("user", user);
                model.addAttribute("manager", this.adminService.getManagerForEdit(username));
                return "admin/addManager";
            case "ADMIN":
                model.addAttribute("user", user);
                model.addAttribute("admin", this.adminService.removeAdminAndReturn(username));
                return "admin/addAdmin";
        }
        return "error";
    }
}
