package com.driver.workFlowManager.repository;

import com.driver.workFlowManager.model.workers.Manager;
import org.springframework.data.repository.CrudRepository;

public interface ManagerRepository extends CrudRepository<Manager, Long> {
    Manager findByUserName(String username);
    boolean existsByUserName(String username);
}
