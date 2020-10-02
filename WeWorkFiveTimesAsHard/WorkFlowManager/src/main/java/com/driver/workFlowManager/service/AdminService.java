package com.driver.workFlowManager.service;

import com.driver.workFlowManager.model.workers.Admin;
import com.driver.workFlowManager.model.workers.StandardWorker;
import com.driver.workFlowManager.model.workers.Users;

public interface AdminService {
    void bindStandardWorkerAndManager(String standardWorkerUsername, String managerUsername);
    void addStandardWorker(Users user, StandardWorker standardWorker);
    void addAdmin(Users user, Admin admin);
    boolean existsByUsername(String username);
    StandardWorker removeStandardWorkerAndReturn(String username);
    Admin removeAdminAndReturn(String username);
    Admin findByUserName(String name);
}
