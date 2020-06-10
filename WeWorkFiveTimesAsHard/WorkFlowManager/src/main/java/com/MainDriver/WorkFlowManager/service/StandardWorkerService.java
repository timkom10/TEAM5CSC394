package com.MainDriver.WorkFlowManager.service;

import com.MainDriver.WorkFlowManager.model.projects.Project;
import com.MainDriver.WorkFlowManager.model.workers.Users;
import com.MainDriver.WorkFlowManager.model.workers.StandardWorker;

import java.util.List;
import java.util.Set;

public interface StandardWorkerService {
    void addStandardWorker(Users user, StandardWorker standardWorker);
    void insertAlteredStandardWorker(StandardWorker standardWorker, String username);
    StandardWorker getByUsername(String username);
    Set<StandardWorker> findAllByUsername(String username);
    Project getStandardWorkerProject(String username);

    List<StandardWorker> getAllStandardWorkersSortedByPoints();
}
