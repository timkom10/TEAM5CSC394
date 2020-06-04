package com.MainDriver.WorkFlowManager.bootStrapData;

import com.MainDriver.WorkFlowManager.model.announcements.Announcement;
import com.MainDriver.WorkFlowManager.model.projects.Project;
import com.MainDriver.WorkFlowManager.model.projects.Tasks;
import com.MainDriver.WorkFlowManager.model.Users;
import com.MainDriver.WorkFlowManager.model.workers.Admin;
import com.MainDriver.WorkFlowManager.model.workers.Manager;
import com.MainDriver.WorkFlowManager.model.workers.StandardWorker;
import com.MainDriver.WorkFlowManager.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/*
    PlaceHolder Data
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
    private final StandardWorkerAnnouncementRepository standardWorkerAnnouncementRepository;
    private final TaskRepository taskRepository;
    private final AdminRepository adminRepository;

    public InitialData
            (
                    UserRepository userRepository,
                    PasswordEncoder passwordEncoder,
                    AnnouncementRepository announcementRepository, ManagerRepository managerRepository,
                    ProjectRepository projectRepository,
                    StandardWorkerRepository standardWorkerRepository,
                    StandardWorkerAnnouncementRepository standardWorkerAnnouncementRepository, TaskRepository taskRepository,
                    AdminRepository adminRepository)
    {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.announcementRepository = announcementRepository;
        this.managerRepository = managerRepository;
        this.projectRepository = projectRepository;
        this.standardWorkerRepository = standardWorkerRepository;
        this.standardWorkerAnnouncementRepository = standardWorkerAnnouncementRepository;
        this.taskRepository = taskRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public void run(String... args) {
        System.out.println("Started in Bootstrap");

        //clear the DB
        this.userRepository.deleteAll();
        this.announcementRepository.deleteAll();
        this.managerRepository.deleteAll();
        this.standardWorkerRepository.deleteAll();
        this.standardWorkerAnnouncementRepository.deleteAll();
        this.projectRepository.deleteAll();
        this.taskRepository.deleteAll();
        this.adminRepository.deleteAll();


       //Any new users need to have password encrypted before db insert
       Users peter = new Users("peter", passwordEncoder.encode("peter12"),"STANDARDWORKER", "none");
       Users admin = new Users("admin", passwordEncoder.encode("peter12"),"ADMIN", "");
       Users manager = new Users("manager", passwordEncoder.encode("peter12"),"MANAGER", "");

       this.userRepository.save(peter);
       this.userRepository.save(admin);
       this.userRepository.save(manager);


        //Make a new manager...
        Manager manager_1 = new Manager();
        manager_1.setUserName("manager");
        manager_1.setFirstName("Kim");
        manager_1.setLastName("Possible");
        manager_1.setHireDate("2002-06-07");
        manager_1.setROLE("MANAGER");

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
        standardWorker.setFirstName("Peter");
        standardWorker.setLastName("Gentile");
        standardWorker.setROLE("Just a worker");
        standardWorker.setManager(manager_1);
        manager_1.getDominion().add(standardWorker);
        standardWorkerRepository.save(standardWorker);

        for(Project project : manager_1.getProjects()) {
            project.getTeamMembers().add(standardWorker);
            standardWorker.setProject(project);
        }


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
        announcement_1.setManager(manager_1);
        announcement_1.setSubject("Adjusted Deadline for Task #34");
        announcement_1.setMessageContent("The deadline for Task #34 has been extended!");
        announcementRepository.save(announcement_1);

        standardWorker.addAnnouncement(announcement_1);
        standardWorkerAnnouncementRepository.saveAll(standardWorker.getStandardWorkerAnnouncements());
        standardWorkerRepository.save(standardWorker);


        //Make an announcement
        Announcement announcement_2 =new Announcement();
        announcement_2.setWrittenBy(manager_1.getFirstName() + " " + manager_1.getLastName());
        announcement_2.setManager(manager_1);
        announcement_2.setSubject("Adjusted Deadline for Task #32");
        announcement_2.setMessageContent("The deadline for Task #32 has been extended!");
        announcementRepository.save(announcement_2);

        standardWorker.addAnnouncement(announcement_2);
        standardWorkerAnnouncementRepository.saveAll(standardWorker.getStandardWorkerAnnouncements());
        standardWorkerRepository.save(standardWorker);


        //Make an announcement
        Announcement announcement_3 =new Announcement();
        announcement_3.setWrittenBy(manager_1.getFirstName() + " " + manager_1.getLastName());
        announcement_3.setManager(manager_1);
        announcement_3.setSubject("Adjusted Deadline for Task #33");
        announcement_3.setMessageContent("The deadline for Task #33 has been extended!");
        announcementRepository.save(announcement_3);

        standardWorker.addAnnouncement(announcement_3);
        standardWorkerAnnouncementRepository.saveAll(standardWorker.getStandardWorkerAnnouncements());
        standardWorkerRepository.save(standardWorker);


        //Make a new admin
        Admin admin_1 = new Admin();
        admin_1.setUserName("admin");
        admin_1.setFirstName("Bobby");
        admin_1.setLastName("Jenks");
        admin_1.setROLE("ADMIN");
        admin_1.setHireDate("09-30-2020");
        adminRepository.save(admin_1);

       //check the Database
    }
}
