package com.MainDriver.WorkFlowManager.repository;

import com.MainDriver.WorkFlowManager.Model.Projects.Tasks;
import org.springframework.data.repository.CrudRepository;


public interface TaskRepository  extends CrudRepository<Tasks, Long> {
}
