package com.driver.workFlowManager.repository;

import com.driver.workFlowManager.model.projects.Project;
import com.driver.workFlowManager.model.workers.Manager;
import com.driver.workFlowManager.model.workers.StandardWorker;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface StandardWorkerRepository extends CrudRepository<StandardWorker, Long> {
    List<StandardWorker> findAllByManagerAndUserNameLike(Manager manager, String username);
    List<StandardWorker> findAllByManager(Manager manager);
    List<StandardWorker> findAllByProject(Project project);
    List<StandardWorker> findAll();

    StandardWorker findByUserName(String username);
    boolean existsByUserName(String username);
}
