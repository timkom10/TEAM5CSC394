package com.driver.workFlowManager.service;

import com.driver.workFlowManager.model.workers.Users;
import com.driver.workFlowManager.model.workers.Manager;

public interface ManagerService {
    void addManager(Users user, Manager manager);

    boolean existsByUsername(String username);

    Manager getByUsername(String username);
}
