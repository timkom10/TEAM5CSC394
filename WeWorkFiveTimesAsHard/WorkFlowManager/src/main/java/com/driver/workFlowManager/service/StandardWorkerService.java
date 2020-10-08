package com.driver.workFlowManager.service;

import com.driver.workFlowManager.model.projects.Project;
import com.driver.workFlowManager.model.workers.Users;
import com.driver.workFlowManager.model.workers.StandardWorker;
import java.util.List;
import java.util.Set;

public interface StandardWorkerService {
    StandardWorker getByUsername(String username);
    List<StandardWorker> getAllFreeWorkersByUsername(String username);
    List<StandardWorker> getAllStandardWorkersSortedByPoints();
    List<StandardWorker> getAllStandardWorkersSortedByPointsByProject(Project project);
    boolean existsByUsername(String username);
}
