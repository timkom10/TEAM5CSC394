package com.MainDriver.WorkFlowManager.BootStrapData;

import com.MainDriver.WorkFlowManager.Model.Announcements.Announcement;
import com.MainDriver.WorkFlowManager.Model.Projects.Project;
import com.MainDriver.WorkFlowManager.Model.Projects.Tasks;
import com.MainDriver.WorkFlowManager.Model.Users;
import com.MainDriver.WorkFlowManager.Model.Workers.Manager;
import com.MainDriver.WorkFlowManager.Model.Workers.StandardWorker;
import com.MainDriver.WorkFlowManager.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/*
    PlaceHolder Data, forget about flyway....
 */
@Component
public class InitialData implements CommandLineRunner
{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AnnouncementRepository announcementRepository;
    private final ManagerRepository managerRepository;
    private final ProjectRepository projectRepository;
    private final StandardWorkerRepository standardWorkerRepository;
    private final TaskRepository taskRepository;

    public InitialData(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AnnouncementRepository announcementRepository, ManagerRepository managerRepository, ProjectRepository projectRepository, StandardWorkerRepository standardWorkerRepository, TaskRepository taskRepository)
    {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.announcementRepository = announcementRepository;
        this.managerRepository = managerRepository;
        this.projectRepository = projectRepository;
        this.standardWorkerRepository = standardWorkerRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public void run(String... args) {
        System.out.println("Started in Bootstrap");
        this.userRepository.deleteAll();

        //Any new users need to have password encrypted before db insert
        Users peter = new Users("peter", passwordEncoder.encode("peter12"),"USER", "none");
        Users admin = new Users("admin", passwordEncoder.encode("admin12"),"ADMIN", "");
        Users manager = new Users("manager", passwordEncoder.encode("manager12"),"MANAGER", "");

       this.userRepository.save(peter);
       this.userRepository.save(admin);
       this.userRepository.save(manager);


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


        //Make a standard worker
        StandardWorker standardWorker = new StandardWorker();
        standardWorker.setUserName("peter");
        standardWorker.setHireDate("02-20-2020");
        standardWorker.setRole("USER");
        standardWorker.setFirstName("peter I guess");
        standardWorker.setLastName("a cool last name");
        manager_1.getDominion().add(standardWorker);

        for(Project project : manager_1.getProjects()) {
            project.getTeamMembers().add(standardWorker);
            standardWorker.setProject(project);
        }
        standardWorkerRepository.save(standardWorker);

        //Assign workers to project
        standardWorker.setProject(project_1);
        project_1.getTeamMembers().add(standardWorker); //could also find users in the manager repo

        //Make a task
        Tasks tasks_1 = new Tasks(manager_1, 450);
        tasks_1.setTaskName("Very important");
        tasks_1.setTaskDescription("Please do this:....");
        tasks_1.setProject(project_1);


        //Assign Workers to task
        standardWorker.getCurrentTasks().add(tasks_1);
        tasks_1.setStandardWorker(standardWorker);
        taskRepository.save(tasks_1);


        //Make an announcement
        Announcement announcement_1 =new Announcement();
        announcement_1.setWrittenBy(manager_1.getFirstName() + " " + manager_1.getLastName());
        announcement_1.setSubject("Ultra important subject");
        announcement_1.setMessageContent("The compiler is ignoring my comments???");
        announcementRepository.save(announcement_1);

        //Push the announcement to all workers within the managers dominion
        for(StandardWorker sw : manager_1.getDominion() ) {
            sw.getAnnouncements().add(announcement_1);
        }

       //check the Database
    }
}
