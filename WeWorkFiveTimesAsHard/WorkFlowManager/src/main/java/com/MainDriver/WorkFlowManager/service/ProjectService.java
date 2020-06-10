package com.MainDriver.WorkFlowManager.service;

import com.MainDriver.WorkFlowManager.model.projects.Milestones;
import com.MainDriver.WorkFlowManager.model.projects.Project;
import com.MainDriver.WorkFlowManager.model.projects.Task;

import java.util.List;
import java.util.Set;

public interface ProjectService {
    void addMileStoneToProject(Project project, Milestones milestone);
    Project getProjectByUsername(String username);

    Milestones getMilestone(Long projectId, Integer milestoneId);
    List<Task> getTasksByMileStoneId(Long projectId, Integer mID);
    Project getByID(Long projectId);
}
