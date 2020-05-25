package com.MainDriver.WorkFlowManager.BootStrapData;

import com.MainDriver.WorkFlowManager.Model.Announcements.Announcement;
import com.MainDriver.WorkFlowManager.Model.Projects.Project;
import com.MainDriver.WorkFlowManager.Model.Projects.Tasks;
import com.MainDriver.WorkFlowManager.Model.Workers.Manager;
import com.MainDriver.WorkFlowManager.Model.Workers.StandardWorker;
import com.MainDriver.WorkFlowManager.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/*
    PlaceHolder Data, forget about flyway....
 */
@Component
public class InitialData implements CommandLineRunner
{

    private final AnnouncementRepository announcementRepository;
    private final ManagerRepository managerRepository;
    private final ProjectRepository projectRepository;
    private final StandardWorkerRepository standardWorkerRepository;
    private final TaskRepository taskRepository;

    public InitialData(
            AnnouncementRepository announcementRepository,
            ManagerRepository managerRepository,
            ProjectRepository projectRepository,
            StandardWorkerRepository standardWorkerRepository,
            TaskRepository taskRepository
    )
    {
        this.announcementRepository = announcementRepository;
        this.managerRepository = managerRepository;
        this.projectRepository = projectRepository;
        this.standardWorkerRepository =standardWorkerRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Started in Bootstrap");

        //Make a new manager...
        Manager manager_1 = new Manager();
        manager_1.setFirstName("Peter");
        manager_1.setLastName("gentile");
        manager_1.setRole("Writes stuff?");
        manager_1.setHireDate("02-20-2020");
        managerRepository.save(manager_1);

        //Make a project
        Project project_1 = new Project();
        project_1.setProjectName("WeWork");
        project_1.setManager(manager_1);

        manager_1.getProjects().add(project_1);
        projectRepository.save(project_1);

        //Make some standard workers
        StandardWorker standardWorker_1 = new StandardWorker();
        standardWorker_1.setFirstName("Not Peter");
        standardWorker_1.setLastName("Not Gentile");
        standardWorker_1.setHireDate("Yesterday?");
        standardWorker_1.setRole("Literally does nothing...");
        manager_1.getDominion().add(standardWorker_1);
        standardWorkerRepository.save(standardWorker_1);

        //Assign workers to project
        standardWorker_1.setProject(project_1);
        project_1.getTeamMembers().add(standardWorker_1); //could also find users in the manager repo

        //Make a task
        Tasks tasks_1 = new Tasks(manager_1, 450);
        tasks_1.setTaskName("Very important");
        tasks_1.setTaskDescription("Please do this:....");
        taskRepository.save(tasks_1);

        //Assign Workers to task
        standardWorker_1.getCurrentTasks().add(tasks_1);

        //Make an announcement
        Announcement announcement_1 =new Announcement();
        announcement_1.setWrittenBy("Could use a worker types first name");
        announcement_1.setSubject("Ultra important subject");
        announcement_1.setMessageContent("The compiler is ignoring my comments???");

        //Push the announcement to all workers within the managers dominion
        for(StandardWorker standardWorker : manager_1.getDominion()) {
            standardWorker.getAnnouncements().add(announcement_1);
        }

        announcementRepository.save(announcement_1);
        //Print out the announcement

    }
}
