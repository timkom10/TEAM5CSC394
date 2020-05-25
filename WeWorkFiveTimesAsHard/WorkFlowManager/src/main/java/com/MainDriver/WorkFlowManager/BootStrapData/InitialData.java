package com.MainDriver.WorkFlowManager.BootStrapData;

import com.MainDriver.WorkFlowManager.Model.Announcements.Announcement;
import com.MainDriver.WorkFlowManager.Model.Projects.Project;
import com.MainDriver.WorkFlowManager.Model.User.Users;
import com.MainDriver.WorkFlowManager.Model.Workers.Manager;
import com.MainDriver.WorkFlowManager.Model.Workers.StandardWorker;
import com.MainDriver.WorkFlowManager.repository.AnnouncementRepository;
import com.MainDriver.WorkFlowManager.repository.ManagerRepository;
import com.MainDriver.WorkFlowManager.repository.ProjectRepository;
import com.MainDriver.WorkFlowManager.repository.UsersRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/*
    PlaceHolder Data, forget about flyway....
 */
@Component
public class InitialData implements CommandLineRunner
{

    private final UsersRepository usersRepository;
    private final AnnouncementRepository announcementRepository;
    private final ManagerRepository managerRepository;
    private final ProjectRepository projectRepository;

    public InitialData(
            UsersRepository usersRepository,
            AnnouncementRepository announcementRepository,
            ManagerRepository managerRepository,
            ProjectRepository projectRepository
    )
    {
        this.usersRepository = usersRepository;
        this.announcementRepository = announcementRepository;
        this.managerRepository = managerRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Started in Bootstrap");

        //Make a user
        Users users_1 = new Users();
        users_1.setFirstName("Peter");
        users_1.setLastName("Gentile");
        users_1.setHireDate("02-20-2020");
        users_1.setRole("Writes stuff?");
        usersRepository.save(users_1);


        //Make a new manager...
        Users users2 = new Users();
        users2.setUserWorkerType(new Manager(users2));
        Manager manager_1 = (Manager)users2.getUserWorkerType();

        
        usersRepository.save(users2);
        managerRepository.save(manager_1 );

        //Make a project
        Project project_1 = new Project(manager_1);
        project_1.setProjectName("A good first Project");

        //Assign workers to project



        //Make a task

        //Assign Workers to task


        //Make an announcement
        Announcement announcement_1 = new Announcement();
        announcement_1.setSubject("Wow!");
        announcement_1.setWrittenBy(users_1.getFirstName() + users_1.getLastName());
        announcement_1.setMessageContent("If the collection is defined using generics to specify the element type, " +
                "the associated target entity type need not be specified; otherwise the target entity class must be " +
                "specified. If the relationship is bidirectional, the mappedBy element must be used to specify the " +
                "relationship field or property of the entity that is the owner of the relationship.");

        announcementRepository.save(announcement_1);


        StandardWorker standardWorker = (StandardWorker)users_1.getUserWorkerType();
        standardWorker.getAnnouncements().add(announcement_1);

        //Print out the announcement
        System.out.println(standardWorker.getAnnouncements().toString());

    }
}
