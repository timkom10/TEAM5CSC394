package com.MainDriver.WorkFlowManager.repository;

import com.MainDriver.WorkFlowManager.Users.WorkFlow_Worker;
import org.springframework.data.repository.CrudRepository;

/*Enables us to add Workers to the Worker SQL table using JPA */
public interface Worker_REPO extends CrudRepository<WorkFlow_Worker, Long> {
}
