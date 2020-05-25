package com.MainDriver.WorkFlowManager.BootStrapData;

import com.MainDriver.WorkFlowManager.Model.Announcements.Announcement;
import com.MainDriver.WorkFlowManager.Model.Projects.Project;
import com.MainDriver.WorkFlowManager.Model.Workers.Manager;
import com.MainDriver.WorkFlowManager.Model.Workers.StandardWorker;
import com.MainDriver.WorkFlowManager.repository.AnnouncementRepository;
import com.MainDriver.WorkFlowManager.repository.ManagerRepository;
import com.MainDriver.WorkFlowManager.repository.ProjectRepository;
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

    public InitialData(
            AnnouncementRepository announcementRepository,
            ManagerRepository managerRepository,
            ProjectRepository projectRepository
    )
    {
        this.announcementRepository = announcementRepository;
        this.managerRepository = managerRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Started in Bootstrap");


        //Make a new manager...

        //Make a project

        //Assign workers to project

        //Make a task

        //Assign Workers to task

        //Make an announcement

        //Print out the announcement

    }
}
