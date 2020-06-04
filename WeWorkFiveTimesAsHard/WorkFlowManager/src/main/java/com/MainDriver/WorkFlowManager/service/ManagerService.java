package com.MainDriver.WorkFlowManager.service;

import com.MainDriver.WorkFlowManager.model.Users;
import com.MainDriver.WorkFlowManager.model.workers.Manager;
import com.MainDriver.WorkFlowManager.repository.ManagerRepository;
import org.springframework.stereotype.Service;

@Service
public class ManagerService {
    private final ManagerRepository managerRepository;

    public ManagerService(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

   public void addManager(Users user, Manager manager)
    {
        if((user != null) && (manager != null))
        {
            manager.setROLE(user.getRoles());
            manager.setUserName(user.getUsername());
            this.managerRepository.save(manager);
        }
    }
}
