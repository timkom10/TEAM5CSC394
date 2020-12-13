package com.driver.workFlowManager.service.implementation;

import com.driver.workFlowManager.model.projects.Milestones;
import com.driver.workFlowManager.model.projects.Project;
import com.driver.workFlowManager.model.projects.Task;
import com.driver.workFlowManager.model.workers.Manager;
import com.driver.workFlowManager.model.workers.StandardWorker;
import com.driver.workFlowManager.repository.ManagerRepository;
import com.driver.workFlowManager.repository.ProjectRepository;
import com.driver.workFlowManager.repository.StandardWorkerRepository;
import com.driver.workFlowManager.service.ProjectService;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
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
    public void addMileStoneToProject(String managerUsername, Milestones milestone) {

        Manager manager = this.managerRepository.findByUserName(managerUsername);
        if(manager != null) {

            Project project = manager.getProject();
            if(project != null)
            {
                /*Check if the mID already exists....if so then we are re-adding an edited milestone*/
                for(Milestones m: manager.getProject().getMilestones())
                {
                    if(m.getId().equals(milestone.getId()))
                    {
                        m.setMilestoneName(milestone.getMilestoneName());
                        m.setDueDate(milestone.getDueDate());
                        m.setDescription(milestone.getDescription());
                        this.projectRepository.save(project);
                        return;
                    }
                }
                 /*This milestone does not yet exist*/
                manager.getProject().addMilestone(milestone);
                this.projectRepository.save(manager.getProject());
            }
        }
    }

    @Override
    public void setTaskToUser(String username, Long projectId, Integer milestoneId, Integer taskId) {
        if(this.projectRepository.existsById(projectId)) {
            Project project = this.projectRepository.getById(projectId);
            for(Task t: project.getTasks()) {
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
    public void setTaskUpForReview(Long projectId, Integer milestoneId, Integer taskId) {
        if(this.projectRepository.existsById(projectId)) {
            Project project = this.projectRepository.getById(projectId);
            for(Task t: project.getTasks()) {
                if(t.getMilestoneId().equals(milestoneId) && t.getTaskId().equals(taskId)) {
                    t.setUpForReview(1);
                    this.projectRepository.save(project);
                    return;
                }
            }
        }
    }

    @Override
    public Task setTaskDoneReturn(Long projectId, Integer milestoneId, Integer taskId) {
        if(this.projectRepository.existsById(projectId)) {
            Project project = this.projectRepository.getById(projectId);
            for(Task t: project.getTasks()) {
                if(t.getMilestoneId().equals(milestoneId) && t.getTaskId().equals(taskId)) {
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
    public Project getProjectByUsername(String username) {
        if(this.standardWorkerRepository.existsByUserName(username)) {
            return standardWorkerRepository.findByUserName(username).getProject();
        }
        else if(this.managerRepository.existsByUserName(username)) {
            return this.managerRepository.findByUserName(username).getProject();
        }
        return null;
    }

    @Override
    public Project getProjectByManagersUsername(String managerUsername) {

        Manager manager = this.managerRepository.findByUserName(managerUsername);
        if(manager != null) {
            Project project = manager.getProject();
            if(project != null) return project;
        }
        return new Project();
    }

    @Override
    public Milestones getMilestone(String managerUsername, Integer milestoneID) {
        Manager manager = this.managerRepository.findByUserName(managerUsername);
        if(manager != null) {
            Project project = manager.getProject();
            if(project != null) {
                for(Milestones m : project.getMilestones()) {
                    if(m.getId().equals(milestoneID)) return m;
                }
            }
        }
        return new Milestones();
    }

    @Override
    public Milestones getMilestone(Long projectId, Integer milestoneId) {
        if(this.projectRepository.existsById(projectId)) {
            Project project = this.projectRepository.getById(projectId);
            for(Milestones m : project.getMilestones()) {
                if(m.getId().equals(milestoneId)) return m;
            }
        }
        return null;
    }

    @Override
    public List<Milestones> getAllMilestonesByProject(String managerUsername) {
        Manager manager = this.managerRepository.findByUserName(managerUsername);
        if(manager != null) {
            Project project = manager.getProject();
            if(project != null) {
                return project.getMilestones();
            }
        }
        return  new ArrayList<>();
    }

    @Override
    public List<Task> getTasksByMileStoneId(Long projectId, Integer mID) {
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
        if(this.projectRepository.existsById(projectId)) {
            return this.projectRepository.getById(projectId);
        }
        return null;
    }

    @Override
    public List<Task> getTasksByUsernameProjectIdAndMilestoneId(String username, Long projectId, Integer milestoneId) {
        List<Task> tasks = new ArrayList<>();
        if(this.projectRepository.existsById(projectId)) {
            Project project = this.projectRepository.getById(projectId);
            for(Task t : project.getTasks()) {
                if(t.getWorker().equals(username) && t.getMilestoneId().equals(milestoneId)) {
                    tasks.add(t);
                }
            }
        }
        return tasks;
    }

    @Override
    public Task getSingleTask(Long projectId, Integer milestoneId, Integer taskId) {
        if(this.projectRepository.existsById(projectId)) {
            Project project = this.projectRepository.getById(projectId);
            for(Task t: project.getTasks()) {
                if(t.getMilestoneId().equals(milestoneId) && t.getTaskId().equals(taskId)) return t;
            }
        }
        return new Task();
    }

    @Override
    public void bindProjectToManager(Project project, String managerUsername) {
        Manager manager =  this.managerRepository.findByUserName(managerUsername);
        if(manager != null && project != null)
        {
            /*Check if we are editing a project, or adding a new one*/
            if(manager.getProject() != null) {
                //we are editing an existing project
                manager.getProject().setProjectName(project.getProjectName());
                manager.getProject().setProjectDescription(project.getProjectDescription());
                this.projectRepository.save(manager.getProject());
                return;
            }
            //adding a new project
            project.setManager(manager);
            manager.setProject(project);

            this.projectRepository.save(project);
            for(StandardWorker standardWorker : manager.getDominion()) {
                standardWorker.setProject(project);
                this.standardWorkerRepository.save(standardWorker);
            }
        }
    }

    @Override
    public void addTaskToMilestone(String managerUsername, Integer milestoneID, Task task) {
        Manager manager = this.managerRepository.findByUserName(managerUsername);
        if(manager != null) {
            Project project = manager.getProject();
            if(project != null) {
              task.setMilestoneId(milestoneID);
              project.addTask(task);
              this.projectRepository.save(project);
            }
        }
    }

    @Override
    public void removeTaskFromMilestone(String managerUsername, Integer taskID, Integer mID) {
        Manager manager = this.managerRepository.findByUserName(managerUsername);
        if(manager != null)
        {
            Project project = manager.getProject();
            if(project != null)
            {
                for(Task t : project.getTasks())
                {
                    if(t.getTaskId().equals(taskID) && t.getMilestoneId().equals(mID))
                    {
                        project.getTasks().remove(t);
                        this.projectRepository.save(project);
                        return;
                    }
                }
            }
        }
    }

    @Override
    public void removeAllTasksWithAssociatedMilestone(String managerUsername, Integer mID) {
        Manager manager = this.managerRepository.findByUserName(managerUsername);
        if(manager != null)
        {
            Project project = manager.getProject();
            if(project != null)
            {
                project.getTasks().removeIf(t -> t.getMilestoneId().equals(mID));
                this.projectRepository.save(project);
            }
        }

    }

    @Override
    public void removeMilestoneFromProject(String managerUsername, Integer mID) {
        Manager manager = this.managerRepository.findByUserName(managerUsername);
        if(manager != null)
        {
            Project project = manager.getProject();
            if(project != null)
            {
                for(Milestones m : project.getMilestones())
                {
                    if(m.getId().equals(mID))
                    {
                        project.getMilestones().remove(m);
                        projectRepository.save(project);
                        return;
                    }
                }
            }
        }
    }

    @Override
    public List<Task> getTaskByUsernameAndMilestoneID(String managerUsername, Integer mID) {

        List<Task> tasks = new ArrayList<>();
        Manager manager = this.managerRepository.findByUserName(managerUsername);
        if(manager != null) {
            Project project = manager.getProject();
            if(project != null) {
                for (Task t : project.getTasks()) {
                    if(t.getMilestoneId().equals(mID)) {
                        tasks.add(t);
                    }
                }
            }
        }
        return tasks;
    }
}
