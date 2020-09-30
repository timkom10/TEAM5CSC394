package com.driver.workFlowManager.service;

import com.driver.workFlowManager.model.workers.Users;
import com.driver.workFlowManager.model.workers.Manager;

import java.util.Set;

public interface ManagerService {
    void addManager(Users user, Manager manager);
    boolean existsByUsername(String username);

    Manager getByUsername(String username);
    Set<Manager> findManagersByUsernameLike(String username);
}
