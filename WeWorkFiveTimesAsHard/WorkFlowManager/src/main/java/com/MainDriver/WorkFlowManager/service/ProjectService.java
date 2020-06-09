package com.MainDriver.WorkFlowManager.service;

import com.MainDriver.WorkFlowManager.model.projects.Milestones;
import com.MainDriver.WorkFlowManager.model.projects.Project;
import com.MainDriver.WorkFlowManager.model.projects.Task;

import java.util.Set;

public interface ProjectService {
    void addMileStoneToProject(Project project, Milestones milestone);
}
