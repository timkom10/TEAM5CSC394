package com.driver.workFlowManager.repository;

import com.driver.workFlowManager.model.workers.Manager;
import org.springframework.data.repository.CrudRepository;
import java.util.Set;

public interface ManagerRepository extends CrudRepository<Manager, Long> {
    Set<Manager> findAllByUserNameLike(String username);
    Manager findByUserName(String username);
    boolean existsByUserName(String username);
}
