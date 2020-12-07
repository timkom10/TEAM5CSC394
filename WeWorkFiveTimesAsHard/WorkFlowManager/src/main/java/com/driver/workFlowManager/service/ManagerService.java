package com.driver.workFlowManager.service;
import com.driver.workFlowManager.model.workers.Manager;
import java.util.Set;

public interface ManagerService {
    void removeWorkerFromManager(String workerUsername, String managerUsername);
    boolean existsByUsername(String username);
    Manager getByUsername(String username);
    Set<Manager> findManagersByUsernameLike(String username);
}
