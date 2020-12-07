package com.driver.workFlowManager.service.implementation;

import com.driver.workFlowManager.model.workers.*;
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

    public Admin findByUserName(String name) {
            return adminRepository.findByUserName(name);
    }

    public void bindStandardWorkerAndManager(String standardWorkerUsername, String managerUsername) {
        StandardWorker standardWorker = this.standardWorkerRepository.findByUserName(standardWorkerUsername);
        Manager manager = this.managerRepository.findByUserName(managerUsername);
        if(standardWorker != null && manager != null) {
            standardWorker.setManager(manager);
            manager.getDominion().add(standardWorker);
            this.managerRepository.save(manager);

            //Any team member added to a manager is auto added to the manager's project that may exist
            standardWorker.setProject(manager.getProject());
            this.standardWorkerRepository.save(standardWorker);
        }
    }

    public StandardWorker getStandardWorkerForEdit(String username) {
        StandardWorker nStandardWorker = new StandardWorker();
        StandardWorker oStandardWorker = this.standardWorkerRepository.findByUserName(username);
        if(oStandardWorker != null) {
            nStandardWorker.setUserName(oStandardWorker.getUserName());
            nStandardWorker.setHireDate(oStandardWorker.getHireDate());
            nStandardWorker.setProject(oStandardWorker.getProject());
            nStandardWorker.setFirstName(oStandardWorker.getFirstName());
            nStandardWorker.setLastName(oStandardWorker.getLastName());
            nStandardWorker.setEmployeeRole(oStandardWorker.getEmployeeRole());
        }
        return nStandardWorker;
    }

    public void addStandardWorker(Users user, StandardWorker standardWorker) {
        if(user == null || standardWorker == null) return;
        /*Check if we are adding a new user, or editing an existing one*/
        user.setRoles("STANDARDWORKER");
        if(standardWorker.getUserName() != null && this.userService.existsByUsername(standardWorker.getUserName())) {
            /*We are editing an existing user*/
            StandardWorker updateThisStandardWorker = this.standardWorkerRepository.findByUserName(standardWorker.getUserName());
            if(updateThisStandardWorker != null) {
                /*Remove the old user associated with the standard worker*/
                this.userService.removeUser(standardWorker.getUserName());

                //Attempt to add in the new user and update the standard worker
                if(this.userService.addUser(user)) {
                    /*Update the fName, lName, uName, Role */
                    updateThisStandardWorker.setUserName(user.getUsername());
                    updateThisStandardWorker.setFirstName(standardWorker.getFirstName());
                    updateThisStandardWorker.setLastName(standardWorker.getLastName());
                    updateThisStandardWorker.setEmployeeRole(standardWorker.getEmployeeRole());
                    this.standardWorkerRepository.save(updateThisStandardWorker);
                }
            }
        }
        else {
            this.addNewUser(standardWorker, user);
        }
    }

    public void addManager(Users user, Manager manager) {
        if(user == null || manager == null) return;
        user.setRoles("MANAGER");

        /*Check if we are adding a new user, or editing an existing one*/
        if(manager.getUserName() != null && this.userService.existsByUsername(manager.getUserName())) {
            /*We are editing a user*/
            Manager updateManager = this.managerRepository.findByUserName(manager.getUserName());
            if(updateManager != null) {
                /*Remove the old user associated with the manager*/
                this.userService.removeUser(manager.getUserName());

                //Attempt to add in the new user and update the manager
                if(this.userService.addUser(user)) {
                    updateManager.setUserName(user.getUsername());
                    updateManager.setLastName(manager.getLastName());
                    updateManager.setFirstName(manager.getFirstName());
                    updateManager.setManagerRole(manager.getManagerRole());
                    this.managerRepository.save(updateManager);
                }
            }
        }
        else {
            this.addNewUser(manager, user);
        }
    }

    public Manager getManagerForEdit(String username) {
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

    public Admin getAdminForEdit(String username) {
        Admin nAdmin = new Admin();
        Admin oAdmin = this.adminRepository.findByUserName(username);
        if(oAdmin != null) {
            nAdmin.setHireDate(oAdmin.getHireDate());
            nAdmin.setUserName(oAdmin.getUserName());
            nAdmin.setFirstName(oAdmin.getFirstName());
            nAdmin.setLastName(oAdmin.getLastName());
            nAdmin.setAdminROLE(oAdmin.getRole());
        }
        return nAdmin;
    }

    public void addAdmin(Users user, Admin admin) {
        if(user == null || admin == null) return;
        user.setRoles("ADMIN");

        /*Check if we are adding a user or editing one*/
        if(admin.getUserName() != null && this.userService.existsByUsername(admin.getUserName())) {

            //We are editing an active user
            Admin updateAdmin = this.adminRepository.findByUserName(admin.getUserName());
            if(updateAdmin != null) {
                /*Remove the old user associated with the admin*/
                this.userService.removeUser(admin.getUserName());

                //Attempt to add in the new user and update the admin
                if(this.userService.addUser(user)) {
                    updateAdmin.setUserName(user.getUsername());
                    updateAdmin.setLastName(admin.getLastName());
                    updateAdmin.setFirstName(admin.getFirstName());
                    updateAdmin.setAdminROLE(admin.getAdminROLE());
                    this.adminRepository.save(updateAdmin);
                }
            }
        }
        else {
            this.addNewUser(admin, user);
        }
    }

    public void addNewUser(WorkerType workerType, Users user) {
        if(workerType == null || user == null) return;

        //we are adding a new user
        if(userService.addUser(user)) {
            /*Get today's date*/
            Date date = Calendar.getInstance().getTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            /*Find out exactly what we are adding, and then add it to its repo*/
            if(workerType instanceof StandardWorker) {
                ((StandardWorker)workerType).setHireDate(dateFormat.format(date));
                ((StandardWorker)workerType).setUserName(user.getUsername());
                this.standardWorkerRepository.save(((StandardWorker)workerType));

            }
            else if(workerType instanceof Manager) {
                ((Manager)workerType).setHireDate(dateFormat.format(date));
                ((Manager)workerType).setUserName(user.getUsername());
                this.managerRepository.save(((Manager)workerType));
            }
            else if(workerType instanceof Admin) {
                ((Admin)workerType).setHireDate(dateFormat.format(date));
                ((Admin)workerType).setUserName(user.getUsername());
                this.adminRepository.save(((Admin)workerType));
            }
        }
    }

    @Override
    public boolean existsByUsername(String username) {
        return this.adminRepository.existsByUserName(username);
    }
}
