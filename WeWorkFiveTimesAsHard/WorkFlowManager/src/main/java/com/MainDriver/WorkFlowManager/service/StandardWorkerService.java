package com.MainDriver.WorkFlowManager.service;

import com.MainDriver.WorkFlowManager.model.workers.Users;
import com.MainDriver.WorkFlowManager.model.workers.StandardWorker;

import java.util.Set;

public interface StandardWorkerService {
    void addStandardWorker(Users user, StandardWorker standardWorker);
    void insertAlteredStandardWorker(StandardWorker standardWorker, String username);
    StandardWorker getByUsername(String username);
    Set<StandardWorker> findAllByUsername(String username);
}
