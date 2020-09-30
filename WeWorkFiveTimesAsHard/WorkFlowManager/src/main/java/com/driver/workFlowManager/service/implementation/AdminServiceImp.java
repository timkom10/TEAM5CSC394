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
            if(standardWorker.getHireDate() == "")
            {
                Date date = Calendar.getInstance().getTime();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                standardWorker.setHireDate(dateFormat.format(date));
            }
            this.standardWorkerRepository.save(standardWorker);
        }
    }

    @Override
    public StandardWorker removeStandardWorker(String username)
    {
        StandardWorker standardWorker = this.standardWorkerRepository.findByUserName(username);
        if(standardWorker != null) this.standardWorkerRepository.delete(standardWorker);
        this.userService.removeUser(username);
        return standardWorker;
    }


    @Override
    public boolean existsByUsername(String username) {
        return this.adminRepository.existsByUserName(username);
    }
}
