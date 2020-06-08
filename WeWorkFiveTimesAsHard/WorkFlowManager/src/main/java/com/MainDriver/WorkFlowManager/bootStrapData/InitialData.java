package com.MainDriver.WorkFlowManager.bootStrapData;
import com.MainDriver.WorkFlowManager.model.messaging.Announcement;
import com.MainDriver.WorkFlowManager.model.messaging.Message;
import com.MainDriver.WorkFlowManager.model.projects.Project;
import com.MainDriver.WorkFlowManager.model.projects.Tasks;
import com.MainDriver.WorkFlowManager.model.workers.Users;
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
    private final ManagerRepository managerRepository;
    private final ProjectRepository projectRepository;
    private final StandardWorkerRepository standardWorkerRepository;
    private final AdminRepository adminRepository;

    public InitialData(UserRepository userRepository, PasswordEncoder passwordEncoder, ManagerRepository managerRepository, ProjectRepository projectRepository, StandardWorkerRepository standardWorkerRepository, AdminRepository adminRepository)
    {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.managerRepository = managerRepository;
        this.projectRepository = projectRepository;
        this.standardWorkerRepository = standardWorkerRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public void run(String... args) {
        System.out.println("Started in Bootstrap");

        //clear the DB
        userRepository.deleteAll();
        managerRepository.deleteAll();
        standardWorkerRepository.deleteAll();
        projectRepository.deleteAll();
        adminRepository.deleteAll();


       //Any new users need to have password encrypted before db insert
       Users peter = new Users("peter", passwordEncoder.encode("peter12"),"STANDARDWORKER", "none");
       Users admin = new Users("admin", passwordEncoder.encode("peter12"),"ADMIN", "");
       Users manager = new Users("manager", passwordEncoder.encode("peter12"),"MANAGER", "");

       userRepository.save(peter);
       userRepository.save(admin);
       userRepository.save(manager);


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
        standardWorker.setEmployeeRole("Just a worker");
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

        //Make a new admin
        Admin admin_1 = new Admin();
        admin_1.setUserName("admin");
        admin_1.setFirstName("Bobby");
        admin_1.setLastName("Jenks");
        admin_1.setROLE("ADMIN");
        admin_1.setHireDate("09-30-2020");
        adminRepository.save(admin_1);


        //Make some messages: STRESS TEST
        Message message_1 = new Message();
        message_1.setFrom(manager.getUsername());
        message_1.setTo(standardWorker.getUserName());
        message_1.setSubject("I Hope I am doing this right..");
        message_1.setMessagePayload("Because, If I am not, I am more than likely doomed");
        standardWorker.addMessage(message_1);
        standardWorkerRepository.save(standardWorker);

        //Make an announcement:
        Announcement announcement_1 = new Announcement();
        announcement_1.setTo(standardWorker.getUserName());
        announcement_1.setFrom(manager_1.getUserName());
        announcement_1.setSubject("Test this");
        announcement_1.setMessagePayload("Wtcfhjbnuiyutcyvjghbknuiyguvtgjh kjyvjg");
        standardWorker.addAnnouncement(announcement_1);
        standardWorkerRepository.save(standardWorker);
       //check the Database
    }
}
