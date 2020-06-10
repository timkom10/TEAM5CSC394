package com.MainDriver.WorkFlowManager.service.implementation;

import com.MainDriver.WorkFlowManager.model.projects.Milestones;
import com.MainDriver.WorkFlowManager.model.projects.Project;
import com.MainDriver.WorkFlowManager.model.projects.Task;
import com.MainDriver.WorkFlowManager.repository.ManagerRepository;
import com.MainDriver.WorkFlowManager.repository.ProjectRepository;
import com.MainDriver.WorkFlowManager.repository.StandardWorkerRepository;
import com.MainDriver.WorkFlowManager.service.ProjectService;
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
    @Transactional
    public Project getProjectByUsername(String username)
    {
        if(this.standardWorkerRepository.existsByUserName(username)) {
            return standardWorkerRepository.findByuserName(username).getProject();
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
}
