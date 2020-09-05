package com.MainDriver.WorkFlowManager.repository;

import com.MainDriver.WorkFlowManager.model.projects.Project;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<Project, Long> {
    boolean existsById(Long id);
    Project getById(Long id);
}
