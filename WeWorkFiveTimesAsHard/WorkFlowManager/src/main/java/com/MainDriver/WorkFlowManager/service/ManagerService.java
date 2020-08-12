package com.MainDriver.WorkFlowManager.service;

import com.MainDriver.WorkFlowManager.model.workers.Users;
import com.MainDriver.WorkFlowManager.model.workers.Manager;

public interface ManagerService {
    void addManager(Users user, Manager manager);

    boolean existsByUsername(String username);

    Manager getByUsername(String username);
}
