package com.MainDriver.WorkFlowManager.repository;

import com.MainDriver.WorkFlowManager.model.workers.StandardWorker;
import org.springframework.data.repository.CrudRepository;

public interface StandardWorkerRepository extends CrudRepository<StandardWorker, Long> {
    StandardWorker findByuserName(String username);
    boolean existsByUserName(String username);
}
