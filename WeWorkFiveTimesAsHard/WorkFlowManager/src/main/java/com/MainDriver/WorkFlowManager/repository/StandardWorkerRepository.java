package com.MainDriver.WorkFlowManager.repository;

import com.MainDriver.WorkFlowManager.model.projects.Project;
import com.MainDriver.WorkFlowManager.model.workers.StandardWorker;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface StandardWorkerRepository extends CrudRepository<StandardWorker, Long> {
    StandardWorker findByuserName(String username);
    boolean existsByUserName(String username);

    List<StandardWorker> findAllByProject(Project project);
    List<StandardWorker> findAll();
}
