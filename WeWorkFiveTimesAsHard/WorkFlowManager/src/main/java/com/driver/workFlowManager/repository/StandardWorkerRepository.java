package com.driver.workFlowManager.repository;

import com.driver.workFlowManager.model.projects.Project;
import com.driver.workFlowManager.model.workers.StandardWorker;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface StandardWorkerRepository extends CrudRepository<StandardWorker, Long> {
    StandardWorker findByUserName(String username);
    boolean existsByUserName(String username);

    List<StandardWorker> findAllByProject(Project project);
    List<StandardWorker> findAll();
}
