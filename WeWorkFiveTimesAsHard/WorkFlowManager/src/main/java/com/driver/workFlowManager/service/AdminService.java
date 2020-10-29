package com.driver.workFlowManager.service;

import com.driver.workFlowManager.model.workers.Admin;

public interface AdminService {
    Admin findByUserName(String name);
    boolean existsByUsername(String username);
}
