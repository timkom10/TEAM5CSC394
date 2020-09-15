package com.driver.workFlowManager.service.implementation;

import com.driver.workFlowManager.model.projects.Milestones;
import com.driver.workFlowManager.model.projects.Project;
import com.driver.workFlowManager.model.projects.Task;
import com.driver.workFlowManager.repository.ManagerRepository;
import com.driver.workFlowManager.repository.ProjectRepository;
import com.driver.workFlowManager.repository.StandardWorkerRepository;
import com.driver.workFlowManager.service.ProjectService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectServiceImp implements ProjectService {

    private final ManagerRepository managerRepository;
    private final StandardWorkerRepository standardWorkerRepository;
    private final ProjectRepository projectRepository;

    public ProjectServiceImp(ManagerRepository managerRepository, StandardWorkerRepository standardWorkerRepository, ProjectRepository projectRepository) {
        this.managerRepository = managerRepository;
        this.standardWorkerRepository = standardWorkerRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public void addMileStoneToProject(Project project, Milestones milestone) {
        if((project != null) && (milestone != null)) {
            project.addMilestone(milestone);
            this.projectRepository.save(project);
        }
    }

    @Override
    public void setTaskToUser(String username, Long projectId, Integer milestoneId, Integer taskId)
    {
        if(this.projectRepository.existsById(projectId))
        {
            Project project = this.projectRepository.getById(projectId);
            for(Task t: project.getTasks())
            {
                if(t.getMilestoneId().equals(milestoneId) && t.getTaskId().equals(taskId)) {
                    t.setIsAssigned(1);
                    t.setWorker(username);
                    this.projectRepository.save(project);
                    return;
                }
            }
        }
    }

    @Override
    public void setTaskUpForReview(Long projectId, Integer milestoneId, Integer taskId)
    {
        if(this.projectRepository.existsById(projectId))
        {
            Project project = this.projectRepository.getById(projectId);
            for(Task t: project.getTasks())
            {
                if(t.getMilestoneId().equals(milestoneId) && t.getTaskId().equals(taskId)) {
                    t.setUpForReview(1);
                    this.projectRepository.save(project);
                    return;
                }
            }
        }
    }

    @Override
    public Task setTaskDoneReturn(Long projectId, Integer milestoneId, Integer taskId)
    {
        if(this.projectRepository.existsById(projectId))
        {
            Project project = this.projectRepository.getById(projectId);
            for(Task t: project.getTasks())
            {
                if(t.getMilestoneId().equals(milestoneId) && t.getTaskId().equals(taskId))
                {
                    t.setIsComplete(1);
                    project.getCompletedTasks().add(t);
                    project.getTasks().remove(t);
                    this.projectRepository.save(project);
                    return t;
                }
            }
        }
        return new Task();
    }

    @Override
    @Transactional
    public Project getProjectByUsername(String username)
    {
        if(this.standardWorkerRepository.existsByUserName(username)) {
            return standardWorkerRepository.findByUserName(username).getProject();
        }
        else if(this.managerRepository.existsByUserName(username)) {
            return this.managerRepository.findByUserName(username).getProject();
        }
        return null;
    }

    @Override
    public Milestones getMilestone(Long projectId, Integer milestoneId)
    {
        if(this.projectRepository.existsById(projectId))
        {
            Project project = this.projectRepository.getById(projectId);
            for(Milestones m : project.getMilestones())
            {
                if(m.getId().equals(milestoneId))
                {
                    return m;
                }
            }

        }
        return null;
    }

    @Override
    public List<Task> getTasksByMileStoneId(Long projectId, Integer mID)
    {
        if(this.projectRepository.existsById(projectId)) {
            Project project = this.projectRepository.getById(projectId);
            List<Task> tasks = new ArrayList<>();
            for(Task t : project.getTasks()) {
                if(t.getMilestoneId().equals(mID)) {
                    tasks.add(t);
                }
            }
            return tasks;
        }
        return null;
    }

    @Override
    public Project getByID(Long projectId) {
        if(this.projectRepository.existsById(projectId))
        {
            return this.projectRepository.getById(projectId);
        }
        return null;
    }

    @Override
    public List<Task> getTasksByUsernameProjectIdAndMilestoneId(String username, Long projectId, Integer milestoneId)
    {
        List<Task> tasks = new ArrayList<>();
        if(this.projectRepository.existsById(projectId))
        {
            Project project = this.projectRepository.getById(projectId);
            for(Task t : project.getTasks())
            {
                if(t.getWorker().equals(username) && t.getMilestoneId().equals(milestoneId))
                {
                    tasks.add(t);
                }
            }
        }
        return tasks;
    }

    @Override
    public Task getSingleTask(Long projectId, Integer milestoneId, Integer taskId) {

        if(this.projectRepository.existsById(projectId))
        {
            Project project = this.projectRepository.getById(projectId);
            for(Task t: project.getTasks())
            {
                if(t.getMilestoneId().equals(milestoneId) && t.getTaskId().equals(taskId))
                {
                    return t;
                }
            }
        }
        return new Task();
    }
}
