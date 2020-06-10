package com.MainDriver.WorkFlowManager.service.implementation;

import com.MainDriver.WorkFlowManager.model.projects.Milestones;
import com.MainDriver.WorkFlowManager.model.projects.Project;
import com.MainDriver.WorkFlowManager.repository.ManagerRepository;
import com.MainDriver.WorkFlowManager.repository.ProjectRepository;
import com.MainDriver.WorkFlowManager.repository.StandardWorkerRepository;
import com.MainDriver.WorkFlowManager.service.ProjectService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
}
