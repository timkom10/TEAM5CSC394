package com.driver.workFlowManager.service;

import com.driver.workFlowManager.model.projects.Milestones;
import com.driver.workFlowManager.model.projects.Project;
import com.driver.workFlowManager.model.projects.Task;
import java.util.List;

public interface ProjectService {
    void addMileStoneToProject(String managerUsername, Milestones milestone);
    void setTaskToUser(String username, Long projectId, Integer milestoneId, Integer taskId);
    void setTaskUpForReview(Long projectId, Integer milestoneId, Integer taskId);
    void bindProjectToManager(Project project, String managerUsername);
    void addTaskToMilestone(String managerUsername, Integer milestoneID, Task task);
    List<Task> getTasksByMileStoneId(Long projectId, Integer mID);
    List<Task> getTasksByUsernameProjectIdAndMilestoneId(String username, Long projectId, Integer milestoneId);
    Task setTaskDoneReturn(Long projectId, Integer milestoneId, Integer taskId);
    Task getSingleTask(Long projectId, Integer milestoneId, Integer taskId);
    Project getProjectByUsername(String username);
    Project getByID(Long projectId);
    Milestones getMilestone(String managerUsername, Integer milestoneID);
    Milestones getMilestone(Long projectId, Integer milestoneId);
}
