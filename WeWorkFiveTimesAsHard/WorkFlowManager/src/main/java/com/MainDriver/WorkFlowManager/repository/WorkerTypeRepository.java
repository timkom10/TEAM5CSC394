package com.MainDriver.WorkFlowManager.repository;

import com.MainDriver.WorkFlowManager.Model.Workers.WorkerTypes;
import org.springframework.data.repository.CrudRepository;

public interface WorkerTypeRepository extends CrudRepository<WorkerTypes, Long> {
}
