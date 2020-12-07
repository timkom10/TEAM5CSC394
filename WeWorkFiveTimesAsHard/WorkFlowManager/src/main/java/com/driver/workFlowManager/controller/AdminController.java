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
        if(admin != null) {
            model.addAttribute("name", principal.getName());
            model.addAttribute("announcements", admin.getAnnouncements());
            return "admin/index";
        }
        return "error";
    }

    @GetMapping("info")
    public String info(Principal principal, Model model) {
        model.addAttribute("name", principal.getName());
        model.addAttribute("workerType", adminService.findByUserName(principal.getName()));
        return "info/info";
    }

    @RequestMapping("Hr")
    public String getHR() {
        return "admin/HumanResources";
    }

    @RequestMapping(value = "RemoveUser", method = RequestMethod.GET)
    public String getUserSearch(Principal principal, Model model, @RequestParam(defaultValue = "") String username) {
        model.addAttribute("name", principal.getName());
        model.addAttribute("users", userService.findByUsername(username));
        return "admin/removeUser";
    }

    @RequestMapping(value = "deleteUser", method = RequestMethod.GET)
    public String getRemoveUser(Principal principal, Model model, String username) {
        userService.removeUser(username);
        model.addAttribute("name", principal.getName());
        model.addAttribute("users", userService.findByUsername(""));
        return "admin/removeUser";
    }

    @RequestMapping("addUser")
    public String getAddUser(Principal principal, Model model) {
        model.addAttribute("name", principal.getName());
        return "admin/selectUserTypeToAdd";
    }

    @RequestMapping(value = "searchTeamMember", method = RequestMethod.GET)
    public String getTeamMemberSearch(Principal principal, Model model, @RequestParam(defaultValue = "") String username) {
        model.addAttribute("name", principal.getName());
        model.addAttribute("users", userService.findByUsername(username));
        return "admin/searchTeamMembers";
    }

    @RequestMapping("addStandardWorker")
    public String getAddStandardWorker(Principal principal, Model model) {
        model.addAttribute("name", principal.getName());
        model.addAttribute("user", new Users());
        model.addAttribute("worker", new StandardWorker());
        return "admin/addStandardWorker";
    }

    @RequestMapping(value = "makeStandardWorker", method = RequestMethod.POST)
    public String getMakeStandardWorker(Principal principal, @ModelAttribute("user") Users user, @ModelAttribute("worker") StandardWorker standardWorker, Model model) {
        this.adminService.addStandardWorker(user, standardWorker);
        model.addAttribute("name", principal.getName());
        model.addAttribute("currentSW", standardWorker.getUserName());
        model.addAttribute("managers", this.managerService.findManagersByUsernameLike(""));
        return "admin/addStandardWorkerSelectManager";
    }

    @RequestMapping(value = "makeStandardWorkerSelectManager", method = RequestMethod.GET)
    public String getMakeStandardWorkerSelectManager(Principal principal, Model model, @RequestParam(defaultValue = "") String username, String standardWorkerUsername) {
        model.addAttribute("name", principal.getName());
        model.addAttribute("currentSW", standardWorkerUsername);
        model.addAttribute("managers", this.managerService.findManagersByUsernameLike(username));
        return "admin/addStandardWorkerSelectManager";
    }

    @RequestMapping(value = "insertStandardWorker", method = RequestMethod.GET)
    public String getInsertStandardWorker(Principal principal, Model model, String standardWorkerUsername, String managerUsername) {
        model.addAttribute("name", principal.getName());
        this.adminService.bindStandardWorkerAndManager(standardWorkerUsername, managerUsername);
        return "redirect:/admin/index";
    }

    @RequestMapping("addManager")
    public String getAddManager(Principal principal, Model model) {
        model.addAttribute("name", principal.getName());
        model.addAttribute("user", new Users());
        model.addAttribute("manager", new Manager());
        return "admin/addManager";
    }

    @RequestMapping("makeManager")
    public String getMakeManagerSelectTeamMembers(Principal principal, @ModelAttribute("user") Users user, @ModelAttribute("manager") Manager manager, Model model) {
        this.adminService.addManager(user, manager);
        model.addAttribute("name", principal.getName());
        model.addAttribute("currentM", user.getUsername());
        model.addAttribute("workers", this.standardWorkerService.getAllFreeWorkersByUsername(""));
        return "admin/addManagerSelectTeamMembers";
    }

    @RequestMapping("makeManagerSelectTeamMembers")
    public String getMakeManagersSelectTeamMembers(Principal principal, Model model, @RequestParam(defaultValue = "") String username, String managerUsername) {
        model.addAttribute("name", principal.getName());
        model.addAttribute("currentM", managerUsername);
        model.addAttribute("workers", this.standardWorkerService.getAllFreeWorkersByUsername(username));
        return "admin/addManagerSelectTeamMembers";
    }

    @RequestMapping("addTeamMemberToManager")
    public String getAddTeamMemberToManager(Principal principal, Model model, String workerUsername, String managerUsername) {
        this.adminService.bindStandardWorkerAndManager(workerUsername, managerUsername);
        model.addAttribute("name", principal.getName());
        model.addAttribute("currentM", managerUsername);
        model.addAttribute("workers", this.standardWorkerService.getAllFreeWorkersByUsername(""));
        return "admin/addManagerSelectTeamMembers";
    }

    @RequestMapping("addManagerRemoveTeamMember")
    public String getAddManagerRemoveTeamMember(Principal principal, Model model, String managerUsername) {
        model.addAttribute("name", principal.getName());
        model.addAttribute("currentM", managerUsername);
        model.addAttribute("workers", this.standardWorkerService.getAllStandardWorkerByManager(this.managerService.getByUsername(managerUsername)));
        return "admin/addManagerRemoveTeamMembers";
    }

    @RequestMapping("removeTeamMemberFromManager")
    public String getRemoveTeamMemberFromManager(Principal principal, Model model, String managerUsername, String workerUsername) {
        this.managerService.removeWorkerFromManager(workerUsername, managerUsername);
        model.addAttribute("name", principal.getName());
        model.addAttribute("currentM", managerUsername);
        model.addAttribute("workers", this.standardWorkerService.getAllStandardWorkerByManager(this.managerService.getByUsername(managerUsername)));
        return "admin/addManagerRemoveTeamMembers";
    }

    @RequestMapping("addAdmin")
    public String getAddAdmin(Principal principal, Model model) {
        model.addAttribute("name", principal.getName());
        model.addAttribute("user", new Users());
        model.addAttribute("admin", new Admin());
        return "admin/addAdmin";
    }

    @RequestMapping("makeAdmin")
    public String getMakeAdmin(@ModelAttribute("user") Users user, @ModelAttribute("admin") Admin admin, Principal principal) {
        this.adminService.addAdmin(user, admin);
        if(principal.getName().equals(user.getUsername())) {
            return "redirect:/login";
        }
        return "redirect:/admin/index";
    }

    @GetMapping("alterWorker")
    public String getAlterWorker(Principal principal, Model model,String username)
    {
        /*Return the original standard worker from service*/
        Users user = this.userService.getByUsername(username);
        switch (user.getRoles()) {
            case "STANDARDWORKER":
                model.addAttribute("user", user);
                model.addAttribute("name", principal.getName());
                model.addAttribute("worker", this.adminService.getStandardWorkerForEdit(username));
                return "admin/addStandardWorker";
            case "MANAGER":
                model.addAttribute("user", user);
                model.addAttribute("name", principal.getName());
                model.addAttribute("manager", this.adminService.getManagerForEdit(username));
                return "admin/addManager";
            case "ADMIN":
                model.addAttribute("user", user);
                model.addAttribute("name", principal.getName());
                model.addAttribute("admin", this.adminService.getAdminForEdit(username));
                return "admin/addAdmin";
        }
        return "error";
    }
}
