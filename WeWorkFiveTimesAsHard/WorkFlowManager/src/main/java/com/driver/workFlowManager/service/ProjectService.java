package com.driver.workFlowManager.service;

import com.driver.workFlowManager.model.projects.Milestones;
import com.driver.workFlowManager.model.projects.Project;
import com.driver.workFlowManager.model.projects.Task;
import java.util.List;

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
