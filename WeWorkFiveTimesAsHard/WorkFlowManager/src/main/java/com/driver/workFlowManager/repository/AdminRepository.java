package com.driver.workFlowManager.repository;

import com.driver.workFlowManager.model.workers.Admin;
import org.springframework.data.repository.CrudRepository;

public interface AdminRepository extends CrudRepository<Admin, Long> {
    Admin findByUserName(String username);
    boolean existsByUserName(String username);
}
