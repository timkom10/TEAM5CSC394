package com.MainDriver.WorkFlowManager.bootStrapData;
import com.MainDriver.WorkFlowManager.model.feedback.AllFeedback;
import com.MainDriver.WorkFlowManager.model.feedback.Feedback;
import com.MainDriver.WorkFlowManager.model.messaging.Announcement;
import com.MainDriver.WorkFlowManager.model.messaging.Message;
import com.MainDriver.WorkFlowManager.model.projects.Milestones;
import com.MainDriver.WorkFlowManager.model.projects.Project;
import com.MainDriver.WorkFlowManager.model.projects.Task;
import com.MainDriver.WorkFlowManager.model.workers.Users;
import com.MainDriver.WorkFlowManager.model.workers.Admin;
import com.MainDriver.WorkFlowManager.model.workers.Manager;
import com.MainDriver.WorkFlowManager.model.workers.StandardWorker;
import com.MainDriver.WorkFlowManager.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;


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
    private final AllFeedbackRepository allFeedbackRepository;

    public InitialData(UserRepository userRepository, PasswordEncoder passwordEncoder, ManagerRepository managerRepository, ProjectRepository projectRepository, StandardWorkerRepository standardWorkerRepository, AdminRepository adminRepository, AllFeedbackRepository allFeedbackRepository)
    {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.managerRepository = managerRepository;
        this.projectRepository = projectRepository;
        this.standardWorkerRepository = standardWorkerRepository;
        this.adminRepository = adminRepository;
        this.allFeedbackRepository = allFeedbackRepository;
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
        allFeedbackRepository.deleteAll();


       //Any new users need to have password encrypted before db insert
       Users peter = new Users("Peter", passwordEncoder.encode("peter12"),"STANDARDWORKER", "none");
       Users admin = new Users("admin", passwordEncoder.encode("peter12"),"ADMIN", "");
       Users manager = new Users("manager", passwordEncoder.encode("peter12"),"MANAGER", "");
       Users tyler = new Users("Tyler", passwordEncoder.encode("tyler"),"STANDARDWORKER", "none");
       Users tim = new Users("Tim", passwordEncoder.encode("tim"),"STANDARDWORKER", "none");
       Users joey = new Users("Joey", passwordEncoder.encode("joey"),"STANDARDWORKER", "none");
       Users paulina = new Users("Paulina", passwordEncoder.encode("paulina"),"STANDARDWORKER", "none");
       Users ricky = new Users("Ricky", passwordEncoder.encode("ricky"),"STANDARDWORKER", "none");
       Users willy = new Users("Willy", passwordEncoder.encode("willy"),"STANDARDWORKER", "none");

       userRepository.save(tyler);
       userRepository.save(tim);
       userRepository.save(joey);
       userRepository.save(paulina);
       userRepository.save(ricky);
       userRepository.save(willy);
       userRepository.save(peter);
       userRepository.save(admin);
       userRepository.save(manager);

       //See below for turning these into Standard Workers

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
        project_1.setProjectName("We(Really)Work");
        project_1.setManager(manager_1);
        project_1.setProjectDescription("A very important project that will beat the competition 100%");
        manager_1.setProject(project_1);
        projectRepository.save(project_1);

        //Make standard workers
        StandardWorker standardWorker = new StandardWorker();
        standardWorker.setUserName("Peter");
        standardWorker.setHireDate("02-20-2020");
        standardWorker.setFirstName("Peter");
        standardWorker.setLastName("Gentile");
        standardWorker.setEmployeeRole("Architect");
        standardWorker.setManager(manager_1);
        standardWorker.setProject(project_1);
        manager_1.getDominion().add(standardWorker);
        project_1.getTeamMembers().add(standardWorker);
        standardWorkerRepository.save(standardWorker);
        projectRepository.save(project_1);

        StandardWorker standardWorker_1 = new StandardWorker();
        standardWorker_1.setUserName("Tyler");
        standardWorker_1.setHireDate("03-30-2020");
        standardWorker_1.setFirstName("Tyler");
        standardWorker_1.setLastName("DemoLastName");
        standardWorker_1.setEmployeeRole("Engine");
        standardWorker_1.setManager(manager_1);
        manager_1.getDominion().add(standardWorker_1);
        standardWorker_1.setProject(project_1);
        project_1.getTeamMembers().add(standardWorker);
        standardWorkerRepository.save(standardWorker_1);
        projectRepository.save(project_1);

        StandardWorker standardWorker_2 = new StandardWorker();
        standardWorker_2.setUserName("Tim");
        standardWorker_2.setHireDate("03-31-2020");
        standardWorker_2.setFirstName("Tim");
        standardWorker_2.setLastName("DemoLastName");
        standardWorker_2.setEmployeeRole("Core");
        standardWorker_2.setManager(manager_1);
        manager_1.getDominion().add(standardWorker_2);
        standardWorker_2.setProject(project_1);
        project_1.getTeamMembers().add(standardWorker);
        standardWorkerRepository.save(standardWorker_2);
        projectRepository.save(project_1);

        StandardWorker standardWorker_3 = new StandardWorker();
        standardWorker_3.setUserName("Joey");
        standardWorker_3.setHireDate("04-01-2020");
        standardWorker_3.setFirstName("Joey");
        standardWorker_3.setLastName("DemoLastName");
        standardWorker_3.setEmployeeRole("Test");
        standardWorker_3.setManager(manager_1);
        standardWorker_3.setProject(project_1);
        manager_1.getDominion().add(standardWorker_3);
        project_1.getTeamMembers().add(standardWorker);
        standardWorkerRepository.save(standardWorker_3);
        projectRepository.save(project_1);

        StandardWorker standardWorker_4 = new StandardWorker();
        standardWorker_4.setUserName("Paulina");
        standardWorker_4.setHireDate("04-02-2020");
        standardWorker_4.setFirstName("Paulina");
        standardWorker_4.setLastName("DemoLastName");
        standardWorker_4.setEmployeeRole("Lead");
        standardWorker_4.setManager(manager_1);
        standardWorker_4.setProject(project_1);
        manager_1.getDominion().add(standardWorker_4);
        project_1.getTeamMembers().add(standardWorker);
        standardWorkerRepository.save(standardWorker_4);
        projectRepository.save(project_1);


        StandardWorker standardWorker_5 = new StandardWorker();
        standardWorker_5.setUserName("Ricky");
        standardWorker_5.setHireDate("04-03-2020");
        standardWorker_5.setFirstName("Ricky");
        standardWorker_5.setLastName("DemoLastName");
        standardWorker_5.setEmployeeRole("Security");
        standardWorker_5.setManager(manager_1);
        standardWorker_5.setProject(project_1);
        manager_1.getDominion().add(standardWorker_5);
        project_1.getTeamMembers().add(standardWorker);
        standardWorkerRepository.save(standardWorker_5);
        projectRepository.save(project_1);

        StandardWorker standardWorker_6 = new StandardWorker();
        standardWorker_6.setUserName("Willy");
        standardWorker_6.setHireDate("04-04-2020");
        standardWorker_6.setFirstName("Willy");
        standardWorker_6.setLastName("DemoLastName");
        standardWorker_6.setEmployeeRole("Front End");
        standardWorker_6.setManager(manager_1);
        standardWorker_6.setProject(project_1);
        manager_1.getDominion().add(standardWorker_6);
        project_1.getTeamMembers().add(standardWorker);
        standardWorkerRepository.save(standardWorker_6);
        projectRepository.save(project_1);


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

        //Make an announcement(s):
       for(StandardWorker sw : manager_1.getDominion()) {
           Announcement announcement_1 = new Announcement();
           announcement_1.setFrom(manager_1.getUserName());
           announcement_1.setSubject("Hello World!");
           announcement_1.setMessagePayload("A very cool project that will no doubt impress!");
           announcement_1.setTo(sw.getUserName());
           sw.addAnnouncement(announcement_1);
           standardWorkerRepository.save(sw);
       }

        //Make some COMPLETED Tasks
        Task task_1 = new Task();
        task_1.setBounty(450);
        task_1.setIsAssigned(1);
        task_1.setProjectId(project_1.getId());
        task_1.setTaskName("Very important");
        task_1.setUrgency("Extremely urgent");
        task_1.setIsComplete(1);
        task_1.setWorker(standardWorker.getUserName());
        project_1.addCompletedTask(task_1);
        projectRepository.save(project_1);

        standardWorker.didTask(task_1);
        this.standardWorkerRepository.save(standardWorker);


        Task task_2 = new Task();
        task_2.setBounty(300);
        task_2.setIsAssigned(1);
        task_2.setProjectId(project_1.getId());
        task_2.setTaskName("Pretty important");
        task_2.setUrgency("Extremely urgent");
        task_2.setIsComplete(1);
        task_2.setWorker(standardWorker_1.getUserName());
        project_1.addCompletedTask(task_2);
        projectRepository.save(project_1);

        standardWorker_1.didTask(task_2);
        this.standardWorkerRepository.save(standardWorker_1);

        Task task_3 = new Task();
        task_3.setBounty(310);
        task_3.setIsAssigned(1);
        task_3.setProjectId(project_1.getId());
        task_3.setTaskName("Important Task Name");
        task_3.setUrgency("Crucial");
        task_3.setIsComplete(1);
        task_3.setWorker(standardWorker_2.getUserName());
        project_1.addCompletedTask(task_3);
        projectRepository.save(project_1);

        standardWorker_2.didTask(task_3);
        this.standardWorkerRepository.save(standardWorker_2);

        Task task_4 = new Task();
        task_4.setBounty(320);
        task_4.setIsAssigned(1);
        task_4.setProjectId(project_1.getId());
        task_4.setTaskName("Also important");
        task_4.setUrgency("Was key");
        task_4.setIsComplete(1);
        task_4.setWorker(standardWorker_3.getUserName());
        project_1.addCompletedTask(task_4);
        projectRepository.save(project_1);

        standardWorker_3.didTask(task_4);
        this.standardWorkerRepository.save(standardWorker_3);

        Task task_5 = new Task();
        task_5.setBounty(330);
        task_5.setIsAssigned(1);
        task_5.setProjectId(project_1.getId());
        task_5.setTaskName("Hard Task Name");
        task_5.setUrgency("very urgent");
        task_5.setIsComplete(1);
        task_5.setWorker(standardWorker_4.getUserName());
        project_1.addCompletedTask(task_5);
        projectRepository.save(project_1);

        standardWorker_4.didTask(task_5);
        this.standardWorkerRepository.save(standardWorker_4);

        Task task_6 = new Task();
        task_6.setBounty(340);
        task_6.setIsAssigned(1);
        task_6.setProjectId(project_1.getId());
        task_6.setTaskName("Hard.Task.Name");
        task_6.setUrgency("Did I say important?");
        task_6.setIsComplete(1);
        task_6.setWorker(standardWorker_5.getUserName());
        project_1.addCompletedTask(task_6);
        projectRepository.save(project_1);

        standardWorker_5.didTask(task_6);
        this.standardWorkerRepository.save(standardWorker_5);

        Task task_7 = new Task();
        task_7.setBounty(350);
        task_7.setIsAssigned(1);
        task_7.setProjectId(project_1.getId());
        task_7.setTaskName("Task Name");
        task_7.setUrgency("Im-port-ant");
        task_7.setIsComplete(1);
        task_7.setWorker(standardWorker_6.getUserName());
        project_1.addCompletedTask(task_7);
        projectRepository.save(project_1);

        standardWorker_6.didTask(task_7);
        this.standardWorkerRepository.save(standardWorker_6);


        //Make some feedback
        Feedback feedback = new Feedback();
        feedback.setFrom(manager_1.getUserName());
        feedback.setTo(standardWorker.getUserName());
        feedback.setSubject("Great Job");
        feedback.setContent("very nice thank you!");
        standardWorker.addFeedback(feedback);
        this.standardWorkerRepository.save(standardWorker);

        AllFeedback allFeedback = new AllFeedback();
        allFeedback.setTo(standardWorker.getUserName());
        allFeedback.setFrom(standardWorker.getUserName());
        allFeedback.setSubject("Congratulating myself?");
        allFeedback.setContent("For demonstrations of course");
        this.allFeedbackRepository.save(allFeedback);
        //check the Database


     //Make a milestone
     Milestones milestone_1 = new Milestones();
     milestone_1.setMilestoneName("Milestone #343");
     milestone_1.setDescription("A very important milestone");
     milestone_1.setDueDate(new Date());

     project_1.addMilestone(milestone_1);
     projectRepository.save(project_1);

     Milestones milestone_2 = new Milestones();
     milestone_2.setMilestoneName("Milestone #342");
     milestone_2.setDescription("Not so very important milestone");
     milestone_2.setDueDate(new Date());

     project_1.addMilestone(milestone_2);
     projectRepository.save(project_1);

     /*Add milestone tasks !COMPLETED => In Progress*/

     Task _task_ = new Task();
     _task_.setBounty(450);
     _task_.setIsAssigned(1);
     _task_.setProjectId(project_1.getId());
     _task_.setTaskName("Very important");
     _task_.setUrgency("Extremely urgent");
     _task_.setWorker(standardWorker.getUserName());
     _task_.setMilestoneId(milestone_1.getId());
     _task_.setTaskDescription("A very long, drawn-out, task description....");
     project_1.addTask(_task_);
     projectRepository.save(project_1);

     Task _task_1 = new Task();
     _task_1.setBounty(440);
     _task_1.setIsAssigned(1);
     _task_1.setProjectId(project_1.getId());
     _task_1.setTaskName("Very important");
     _task_1.setUrgency("is important");
     _task_1.setMilestoneId(milestone_1.getId());
     _task_1.setWorker(standardWorker_1.getUserName());
     _task_1.setTaskDescription("A very long, drawn-out, task description....");
     project_1.addTask(_task_1);

     projectRepository.save(project_1);


     Task _task_2 = new Task();
     _task_2.setBounty(440);
     _task_2.setIsAssigned(1);
     _task_2.setProjectId(project_1.getId());
     _task_2.setTaskName("--important--");
     _task_2.setUrgency("crucial");
     _task_2.setMilestoneId(milestone_1.getId());
     _task_2.setWorker(standardWorker_2.getUserName());
     _task_2.setTaskDescription("A very long, drawn-out, task description....");
     project_1.addTask(_task_2);

     projectRepository.save(project_1);

     Task _task_3 = new Task();
     _task_3.setBounty(440);
     _task_3.setIsAssigned(1);
     _task_3.setProjectId(project_1.getId());
     _task_3.setTaskName("--Task Name--");
     _task_3.setUrgency("urgent");
     _task_3.setMilestoneId(milestone_1.getId());
     _task_3.setWorker(standardWorker_3.getUserName());
     _task_3.setTaskDescription("A very long, drawn-out, task description....");
     project_1.addTask(_task_3);

     projectRepository.save(project_1);


     Task _task_4 = new Task();
     _task_4.setBounty(440);
     _task_4.setIsAssigned(1);
     _task_4.setProjectId(project_1.getId());
     _task_4.setTaskName("A task Name");
     _task_4.setUrgency("An urgency");
     _task_4.setMilestoneId(milestone_1.getId());
     _task_4.setWorker(standardWorker_4.getUserName());
     _task_4.setTaskDescription("A very long, drawn-out, task description....");
     project_1.addTask(_task_4);

     projectRepository.save(project_1);


     Task _task_5 = new Task();
     _task_5.setBounty(440);
     _task_5.setIsAssigned(0);
     _task_5.setProjectId(project_1.getId());
     _task_5.setTaskName("Demo task Name");
     _task_5.setUrgency("Demo urgency");
     _task_5.setMilestoneId(milestone_1.getId());
     _task_5.setWorker("Available");
     _task_5.setTaskDescription("A very long, drawn-out, task description....");
     project_1.addTask(_task_5);

     projectRepository.save(project_1);

    }
}
