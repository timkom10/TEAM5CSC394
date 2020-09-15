package com.driver.workFlowManager.service;

import com.driver.workFlowManager.model.projects.Project;
import com.driver.workFlowManager.model.workers.Users;
import com.driver.workFlowManager.model.workers.StandardWorker;

import java.util.List;
import java.util.Set;

public interface StandardWorkerService {
    StandardWorker getByUsername(String username);
    Set<StandardWorker> findAllByUsername(String username);

    List<StandardWorker> getAllStandardWorkersSortedByPoints();
    List<StandardWorker> getAllStandardWorkersSortedByPointsByProject(Project project);

    void addStandardWorker(Users user, StandardWorker standardWorker);
    void insertAlteredStandardWorker(StandardWorker standardWorker, String username);

    boolean existsByUsername(String username);
}
