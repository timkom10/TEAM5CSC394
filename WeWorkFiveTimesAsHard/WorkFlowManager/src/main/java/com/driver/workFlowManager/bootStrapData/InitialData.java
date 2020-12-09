package com.driver.workFlowManager.bootStrapData;
import com.driver.workFlowManager.model.feedback.AllFeedback;
import com.driver.workFlowManager.model.feedback.Feedback;
import com.driver.workFlowManager.model.messaging.Announcement;
import com.driver.workFlowManager.model.messaging.Message;
import com.driver.workFlowManager.model.projects.Milestones;
import com.driver.workFlowManager.model.projects.Project;
import com.driver.workFlowManager.model.projects.Task;
import com.driver.workFlowManager.model.workers.Users;
import com.driver.workFlowManager.model.workers.Admin;
import com.driver.workFlowManager.model.workers.Manager;
import com.driver.workFlowManager.model.workers.StandardWorker;
import com.driver.workFlowManager.repository.*;
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

    public InitialData(UserRepository userRepository, PasswordEncoder passwordEncoder, ManagerRepository managerRepository, ProjectRepository projectRepository, StandardWorkerRepository standardWorkerRepository, AdminRepository adminRepository, AllFeedbackRepository allFeedbackRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.managerRepository = managerRepository;
        this.projectRepository = projectRepository;
        this.standardWorkerRepository = standardWorkerRepository;
        this.adminRepository = adminRepository;
        this.allFeedbackRepository = allFeedbackRepository;
    }

    public StandardWorker makeStandardWorker(String userN, String firstN, String lastN, String hireD, String empR, Manager manager, Project project) {
      StandardWorker standardWorker = new StandardWorker();
      standardWorker.setUserName(userN);
      standardWorker.setFirstName(firstN);
      standardWorker.setLastName(lastN);
      standardWorker.setHireDate(hireD);
      standardWorker.setEmployeeRole(empR);
      standardWorker.setManager(manager);
      standardWorker.setProject(project);

      this.standardWorkerRepository.save(standardWorker);
      if(project != null) {
       project.getTeamMembers().add(standardWorker);
       this.projectRepository.save(project);
      }
      if(manager != null) {
        manager.getDominion().add(standardWorker);
        this.managerRepository.save(manager);
      }
      return standardWorker;
    }

    public void makeTask(String name, String urgency, String taskDescription, Integer mID, int bounty, int assigned, int complete, Project project, StandardWorker standardWorker)
    {
      Task task = new Task();
      task.setTaskName(name);
      task.setUrgency(urgency);
      task.setTaskDescription(taskDescription);
      task.setMilestoneId(mID);
      task.setBounty(bounty);
      task.setIsAssigned(assigned);
      task.setIsComplete(complete);


      if(standardWorker != null)
          task.setWorker(standardWorker.getUserName());
      else
        task.setWorker("Available");

      if(complete > 0 && standardWorker != null) {
          standardWorker.didTask(task);
          this.standardWorkerRepository.save(standardWorker);
      }

      if(project != null)
      {
       task.setProjectId(project.getId());
       if(complete > 0) {
        project.addCompletedTask(task);
       }
       else {
        project.addTask(task);
       }
        this.projectRepository.save(project);
      }
    }

    @Override
    public void run(String... args)
    {
       System.out.println("Started in Bootstrap");

       //clear the DB
       userRepository.deleteAll();
       managerRepository.deleteAll();
       standardWorkerRepository.deleteAll();
       projectRepository.deleteAll();
       adminRepository.deleteAll();
       allFeedbackRepository.deleteAll();

       //Any new users need to have password encrypted before db insert
       Users peter = new Users("Peter", passwordEncoder.encode("peter"),"STANDARDWORKER", "none");
       Users admin = new Users("admin", passwordEncoder.encode("peter"),"ADMIN", "");
       Users michael = new Users("michael", passwordEncoder.encode("peter"),"MANAGER", "");
       Users tyler = new Users("Tyler", passwordEncoder.encode("tyler"),"STANDARDWORKER", "none");
       Users tim = new Users("Tim", passwordEncoder.encode("tim"),"STANDARDWORKER", "none");
       Users joey = new Users("Joey", passwordEncoder.encode("joey"),"STANDARDWORKER", "none");
       Users paulina = new Users("Paulina", passwordEncoder.encode("paulina"),"STANDARDWORKER", "none");
       Users ricky = new Users("Ricky", passwordEncoder.encode("ricky"),"STANDARDWORKER", "none");
       Users willy = new Users("Willy", passwordEncoder.encode("willy"),"STANDARDWORKER", "none");
       Users billy = new Users("Billy", passwordEncoder.encode("billy"),"STANDARDWORKER", "none");
       Users kim = new Users("Kim", passwordEncoder.encode("kim"), "MANAGER", "none");

       this.userRepository.save(kim);
       this.userRepository.save(billy);
       this.userRepository.save(willy);
       this.userRepository.save(ricky);
       this.userRepository.save(paulina);
       this.userRepository.save(joey);
       this.userRepository.save(tim);
       this.userRepository.save(tyler);
       this.userRepository.save(michael);
       this.userRepository.save(admin);
       this.userRepository.save(peter);

       //Make a new admin
       Admin admin_1 = new Admin();
       admin_1.setUserName("admin");
       admin_1.setFirstName("Bobby");
       admin_1.setLastName("Jenks");
       admin_1.setAdminROLE("ADMIN");
       admin_1.setHireDate("2020-09-30");
       adminRepository.save(admin_1);

       //Make two new managers...
       Manager manager_1 = new Manager();
       manager_1.setUserName("michael");
       manager_1.setFirstName("Michael");
       manager_1.setLastName("Scott");
       manager_1.setHireDate("2004-03-24");
       manager_1.setManagerRole("MANAGER");
       managerRepository.save(manager_1);

       Manager manager_2 = new Manager();
       manager_2.setUserName("kim");
       manager_2.setFirstName("Kim");
       manager_2.setLastName("DemoLastName");
       manager_2.setHireDate("2020-11-28");
       manager_2.setManagerRole("DemoLeadName");
       managerRepository.save(manager_2);

       //Make a project
       Project project_1 = new Project();
       project_1.setProjectName("We(Really)Work");
       project_1.setManager(manager_1);
       project_1.setProjectDescription("A very important project that will beat the competition 100%");
       manager_1.setProject(project_1);
       projectRepository.save(project_1);

       //Make standard workers
       StandardWorker standardWorker_0 = this.makeStandardWorker("peter", "Peter", "gentile", "2020-02-20", "Architect", manager_1, project_1);
       StandardWorker standardWorker_1 = this.makeStandardWorker("tyler", "Tyler", "DemoLastName", "2020-03-30", "Engine", manager_1, project_1);
       StandardWorker standardWorker_2 = this.makeStandardWorker("tim", "Tim", "DemoLastName", "2020-04-30", "Core", manager_1, project_1);
       StandardWorker standardWorker_3 = this.makeStandardWorker("joey", "Joey", "DemoLastName", "2020-03-23", "Test", manager_1, project_1);
       StandardWorker standardWorker_4 = this.makeStandardWorker("paulina", "Paulina", "DemoLastName", "2020-03-31", "Lead", manager_1, project_1);
       StandardWorker standardWorker_5 = this.makeStandardWorker("ricky", "Ricky", "DemoLastName", "2020-04-01", "Security", manager_1, project_1);
       StandardWorker standardWorker_6 = this.makeStandardWorker("willy", "Willy", "DemoLastName", "2020-04-02", "Front End", manager_1, project_1);
       StandardWorker standardWorker_7 = this.makeStandardWorker("billy", "billy", "demoLName", "2020-07-24", "BackEnd", manager_1, null);

       //Make a message
       Message message_1 = new Message();
       message_1.setFrom(manager_1.getUserName());
       message_1.setTo(standardWorker_0.getUserName());
       message_1.setSubject("I Hope I am doing this right..");
       message_1.setMessagePayload("Because, If I am not, I am more than likely doomed");
       standardWorker_0.addMessage(message_1);
       standardWorkerRepository.save(standardWorker_0);

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
       this.makeTask("Demo Test Task Name", "Demo Test Urgency","", null,450, 1,1, project_1, standardWorker_0);
       this.makeTask("Pretty important", "Extremely urgent", "",null, 300, 1, 1, project_1, standardWorker_1);
       this.makeTask("Important Task Name", "Crucial", "",null, 310, 1, 1, project_1, standardWorker_2);
       this.makeTask("Test Task Name", "Important", "",null, 320, 1, 1, project_1, standardWorker_3);
       this.makeTask("Hard Task Name", "Very-Urgent", "",null, 330, 1, 1, project_1, standardWorker_4);
       this.makeTask("Also Hard Task Name", "Did I say important?", "",null, 340, 1, 1, project_1, standardWorker_5);
       this.makeTask("Task Name", "Im-port-ant","", null, 350, 1, 1, project_1, standardWorker_6);

       //Make a few milestones
       Milestones milestone_1 = new Milestones();
       milestone_1.setMilestoneName("Milestone #343");
       milestone_1.setDescription("A very important milestone");
       milestone_1.setDueDate("2021-02-28");
       project_1.addMilestone(milestone_1);
       projectRepository.save(project_1);

       Milestones milestone_2 = new Milestones();
       milestone_2.setMilestoneName("Milestone #342");
       milestone_2.setDescription("Not so very important milestone");
       milestone_2.setDueDate("2030-01-22");
       project_1.addMilestone(milestone_2);
       projectRepository.save(project_1);

       //Add some milestone specific tasks
       this.makeTask("Demo Task Name", "Extremely urgent","Test Task Description", milestone_1.getId(), 450, 1, 0, project_1, standardWorker_0);
       this.makeTask("Test Task Name", "is Important","Drawn-out, task description", milestone_1.getId(), 440, 1, 0, project_1, standardWorker_1);
       this.makeTask("--Important--", "crucial","Drawn-out, task description", milestone_1.getId(), 430, 1, 0, project_1, standardWorker_2);
       this.makeTask("--Task Name--", "urgent","Drawn-out, task description", milestone_1.getId(), 420, 1, 0, project_1, standardWorker_3);
       this.makeTask("Some Task Name", "urgent","Drawn-out, task description", milestone_1.getId(), 410, 1, 0, project_1, standardWorker_4);
       this.makeTask("A Task Name", "urgent","Drawn-out, task description", milestone_1.getId(), 400, 0, 0, project_1, null);

       //Make some feedback types
       Feedback feedback = new Feedback();
       feedback.setFrom(manager_1.getUserName());
       feedback.setTo(standardWorker_0.getUserName());
       feedback.setSubject("Great Job");
       feedback.setContent("very nice thank you!");
       standardWorker_0.addFeedback(feedback);
       this.standardWorkerRepository.save(standardWorker_0);

       AllFeedback allFeedback = new AllFeedback();
       allFeedback.setTo(standardWorker_0.getUserName());
       allFeedback.setFrom(standardWorker_0.getUserName());
       allFeedback.setSubject("Congratulating myself?");
       allFeedback.setContent("For demonstrations of course");
       allFeedback.setDate(new Date());
       this.allFeedbackRepository.save(allFeedback);
    }
}
