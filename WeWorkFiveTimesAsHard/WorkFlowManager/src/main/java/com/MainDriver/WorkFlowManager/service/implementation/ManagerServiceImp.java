package com.MainDriver.WorkFlowManager.service.implementation;

import com.MainDriver.WorkFlowManager.model.workers.StandardWorker;
import com.MainDriver.WorkFlowManager.model.workers.Users;
import com.MainDriver.WorkFlowManager.model.workers.Manager;
import com.MainDriver.WorkFlowManager.repository.ManagerRepository;
import com.MainDriver.WorkFlowManager.service.ManagerService;
import org.springframework.stereotype.Service;

@Service
public class ManagerServiceImp implements ManagerService {
    private final ManagerRepository managerRepository;

    public ManagerServiceImp(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    @Override
    public void addManager(Users user, Manager manager) {
        if((user != null) && (manager != null)) {
            manager.setROLE(user.getRoles());
            manager.setUserName(user.getUsername());
            this.managerRepository.save(manager);
        }
    }

    @Override
    public boolean existsByUsername(String username) {
        return this.managerRepository.existsByUserName(username);
    }

    @Override
    public Manager getByUsername(String username) {
        Manager manager = this.managerRepository.findByUserName(username);
        if(manager != null) {
            return  manager;
        }
        return new Manager();
    }
}
