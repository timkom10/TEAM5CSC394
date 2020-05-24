package com.MainDriver.WorkFlowManager.repository;

import com.MainDriver.WorkFlowManager.Model.Projects.Project;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<Project, Long> {
}
