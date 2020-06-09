package com.MainDriver.WorkFlowManager.service.implementation;

import com.MainDriver.WorkFlowManager.model.projects.Milestones;
import com.MainDriver.WorkFlowManager.model.projects.Project;
import com.MainDriver.WorkFlowManager.repository.ProjectRepository;
import com.MainDriver.WorkFlowManager.service.ProjectService;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImp implements ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectServiceImp(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public void addMileStoneToProject(Project project, Milestones milestone) {
        if((project != null) && (milestone != null)) {
            project.addMilestone(milestone);
            this.projectRepository.save(project);
        }
    }
}
