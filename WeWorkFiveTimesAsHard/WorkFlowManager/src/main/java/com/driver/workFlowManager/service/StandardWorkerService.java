package com.driver.workFlowManager.service;

import com.driver.workFlowManager.model.projects.Project;
import com.driver.workFlowManager.model.workers.Manager;
import com.driver.workFlowManager.model.workers.StandardWorker;
import java.util.List;

public interface StandardWorkerService {
    StandardWorker getByUsername(String username);
    List<StandardWorker> getAllFreeWorkersByUsername(String username);
    List<StandardWorker> getAllStandardWorkersSortedByPoints();
    List<StandardWorker> getAllStandardWorkersSortedByPointsByProject(Project project);
    List<StandardWorker> getAllStandardWorkerByManager(Manager manager);
    boolean existsByUsername(String username);
}
