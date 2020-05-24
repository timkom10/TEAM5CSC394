package com.MainDriver.WorkFlowManager.BootStrapData;

import com.MainDriver.WorkFlowManager.Model.Announcements.Announcement;
import com.MainDriver.WorkFlowManager.Model.User.Users;
import com.MainDriver.WorkFlowManager.Model.Workers.StandardWorker;
import com.MainDriver.WorkFlowManager.repository.AnnouncementRepository;
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

    public InitialData(
            UsersRepository usersRepository,
            AnnouncementRepository announcementRepository
    )
    {
        this.usersRepository = usersRepository;
        this.announcementRepository = announcementRepository;
    }

    @Override
    public void run(String... args) throws Exception
    {
        System.out.println("Started in Bootstrap");

        //Make a user
        Users users = new Users();
        users.setFirstName("Peter");
        users.setLastName("Gentile");
        users.setHireDate("02-20-2020");
        users.setRole("Writes stuff?");
        StandardWorker standardWorker = (StandardWorker)users.getUserWorkerType();
        standardWorker.setTeam("WeWorkFiveTimesAsHard");
        usersRepository.save(users);
        System.out.println("Amount of Users:" + usersRepository.count());

        //Make an announcement:
        Announcement announcement_1 = new Announcement();
        announcement_1.setSubject("Wow!");
        announcement_1.setWrittenBy(users.getFirstName() + users.getLastName());
        announcement_1.setMessageContent("If the collection is defined using generics to specify the element type, " +
                "the associated target entity type need not be specified; otherwise the target entity class must be " +
                "specified. If the relationship is bidirectional, the mappedBy element must be used to specify the " +
                "relationship field or property of the entity that is the owner of the relationship.");

        announcementRepository.save(announcement_1);
        standardWorker.getAnnouncements().add(announcement_1);

        //Print out the announcement
        System.out.println(standardWorker.getAnnouncements().toString());

    }
}
