package com.MainDriver.WorkFlowManager.service;

import com.MainDriver.WorkFlowManager.model.workers.Admin;

public interface AdminService {
    Admin findByUserName(String name);

    boolean existsByUsername(String username);
}
