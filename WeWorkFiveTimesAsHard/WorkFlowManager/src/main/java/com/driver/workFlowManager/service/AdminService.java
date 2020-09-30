package com.driver.workFlowManager.service;

import com.driver.workFlowManager.model.workers.Admin;
import com.driver.workFlowManager.model.workers.StandardWorker;
import com.driver.workFlowManager.model.workers.Users;

public interface AdminService {
    Admin findByUserName(String name);
    void bindStandardWorkerAndManager(String standardWorkerUsername, String managerUsername);
    void addStandardWorker(Users user, StandardWorker standardWorker);
    StandardWorker removeStandardWorker(String username);
    boolean existsByUsername(String username);
}
