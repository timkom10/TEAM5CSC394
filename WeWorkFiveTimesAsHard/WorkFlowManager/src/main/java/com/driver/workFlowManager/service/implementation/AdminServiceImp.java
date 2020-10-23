package com.driver.workFlowManager.service.implementation;

import com.driver.workFlowManager.model.workers.Admin;
import com.driver.workFlowManager.model.workers.Manager;
import com.driver.workFlowManager.model.workers.StandardWorker;
import com.driver.workFlowManager.model.workers.Users;
import com.driver.workFlowManager.repository.AdminRepository;
import com.driver.workFlowManager.repository.ManagerRepository;
import com.driver.workFlowManager.repository.StandardWorkerRepository;
import com.driver.workFlowManager.service.AdminService;
import com.driver.workFlowManager.service.UserService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
@Transactional
public class AdminServiceImp implements AdminService {
    private final AdminRepository adminRepository;
    private final StandardWorkerRepository standardWorkerRepository;
    private final ManagerRepository managerRepository;
    private final UserService userService;

    public AdminServiceImp(AdminRepository adminRepository, StandardWorkerRepository standardWorkerRepository, ManagerRepository managerRepository, UserService userService) {
        this.adminRepository = adminRepository;
        this.standardWorkerRepository = standardWorkerRepository;
        this.managerRepository = managerRepository;
        this.userService = userService;
    }

    @Override
    public Admin findByUserName(String name) {
            return adminRepository.findByUserName(name);
    }

    @Override
    public void bindStandardWorkerAndManager(String standardWorkerUsername, String managerUsername)
    {
        StandardWorker standardWorker = this.standardWorkerRepository.findByUserName(standardWorkerUsername);
        Manager manager = this.managerRepository.findByUserName(managerUsername);
        if(standardWorker != null && manager != null)
        {
            standardWorker.setManager(manager);
            manager.getDominion().add(standardWorker);
            this.standardWorkerRepository.save(standardWorker);
            this.managerRepository.save(manager);
        }
    }

    @Override
    public void addStandardWorker(Users user, StandardWorker standardWorker) {
        if(user == null || standardWorker == null) return;
        user.setRoles("STANDARDWORKER");
        if(userService.addUser(user))
        {
            standardWorker.setUserName(user.getUsername());
            if(standardWorker.getHireDate().equals(""))
            {
                Date date = Calendar.getInstance().getTime();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                standardWorker.setHireDate(dateFormat.format(date));
            }
            this.standardWorkerRepository.save(standardWorker);
        }
    }

    @Override
    public void addManager(Users user, Manager manager)
    {
        if(user == null || manager == null) return;

        /*Check if we are adding a new user, or editing an existing one*/
        if(manager.getUserName() != null && this.userService.existsByUsername(manager.getUserName()))
        {
            /*We are editing a user*/
            user.setRoles("MANAGER");
            Manager updateManager = this.managerRepository.findByUserName(manager.getUserName());

            if(updateManager != null && userService.addUser(user))
            {
                if(!manager.getUserName().equals(user.getUsername())) {
                    /*Remove the old user, and add in the new one*/
                    this.userService.removeUser(manager.getUserName());
                    this.userService.addUser(user);
                }
                updateManager.setUserName(user.getUsername());
                updateManager.setLastName(manager.getLastName());
                updateManager.setFirstName(manager.getFirstName());
                updateManager.setManagerRole(manager.getManagerRole());
                this.managerRepository.save(updateManager);
            }
        }
        else
        {
            user.setRoles("MANAGER");
            if(userService.addUser(user))
            {
                manager.setUserName(user.getUsername());
                Date date = Calendar.getInstance().getTime();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                manager.setHireDate(dateFormat.format(date));
                this.managerRepository.save(manager);
            }
        }
    }

    @Override
    public void addAdmin(Users user, Admin admin)
    {
        if(user == null || admin == null) return;
        user.setRoles("ADMIN");
        if(userService.addUser(user))
        {
            admin.setUserName(user.getUsername());
            if(admin.getHireDate().equals(""))
            {
                Date date = Calendar.getInstance().getTime();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                admin.setHireDate(dateFormat.format(date));
            }
            this.adminRepository.save(admin);
        }
    }

    @Override
    public StandardWorker removeStandardWorkerAndReturn(String username)
    {
        StandardWorker standardWorker = this.standardWorkerRepository.findByUserName(username);
        if(standardWorker != null) this.standardWorkerRepository.delete(standardWorker);
        this.userService.removeUser(username);
        return standardWorker;
    }

    @Override
    public Manager getManagerForEdit(String username)
    {
        Manager nManager = new Manager();
        Manager oManager = this.managerRepository.findByUserName(username);
        if(oManager != null) {
            nManager.setHireDate(oManager.getHireDate());
            nManager.setUserName(oManager.getUserName());
            nManager.setFirstName(oManager.getFirstName());
            nManager.setLastName(oManager.getLastName());
            nManager.setManagerRole(oManager.getManagerRole());
        }
        return nManager;
    }

    @Override
    public Admin removeAdminAndReturn(String username)
    {
        Admin admin = this.adminRepository.findByUserName(username);
        if(admin != null) this.adminRepository.delete(admin);
        this.userService.removeUser(username);
        return admin;
    }


    @Override
    public boolean existsByUsername(String username) {
        return this.adminRepository.existsByUserName(username);
    }
}
