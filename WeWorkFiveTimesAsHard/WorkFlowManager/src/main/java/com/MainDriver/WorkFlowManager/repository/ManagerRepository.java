package com.MainDriver.WorkFlowManager.repository;

import com.MainDriver.WorkFlowManager.model.workers.Manager;
import org.springframework.data.repository.CrudRepository;

public interface ManagerRepository extends CrudRepository<Manager, Long> {
    Manager findByUserName(String username);
}
