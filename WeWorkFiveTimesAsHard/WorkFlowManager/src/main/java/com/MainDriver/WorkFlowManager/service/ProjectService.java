package com.MainDriver.WorkFlowManager.service;

import com.MainDriver.WorkFlowManager.model.projects.Milestones;
import com.MainDriver.WorkFlowManager.model.projects.Project;
import com.MainDriver.WorkFlowManager.model.projects.Task;

import java.util.List;
import java.util.Set;

public interface ProjectService {
    void addMileStoneToProject(Project project, Milestones milestone);
    void setTaskToUser(String username, Long projectId, Integer milestoneId, Integer taskId);
    void setTaskUpForReview(Long projectId, Integer milestoneId, Integer taskId);
    Task setTaskDoneReturn(Long projectId, Integer milestoneId, Integer taskId);
    Project getProjectByUsername(String username);
    Milestones getMilestone(Long projectId, Integer milestoneId);
    Project getByID(Long projectId);
    List<Task> getTasksByMileStoneId(Long projectId, Integer mID);
    List<Task> getTasksByUsernameProjectIdAndMilestoneId(String username, Long projectId, Integer milestoneId);
    Task getSingleTask(Long projectId, Integer milestoneId, Integer taskId);

}
