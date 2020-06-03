package com.MainDriver.WorkFlowManager.repository;

import com.MainDriver.WorkFlowManager.model.workers.Admin;
import org.springframework.data.repository.CrudRepository;

public interface AdminRepository extends CrudRepository<Admin, Long> {
    Admin findByUserName(String username);
}
