package com.MainDriver.WorkFlowManager.service;

import com.MainDriver.WorkFlowManager.model.Users;
import com.MainDriver.WorkFlowManager.model.announcements.Announcement;
import com.MainDriver.WorkFlowManager.model.announcements.StandardWorkerAnnouncements;
import com.MainDriver.WorkFlowManager.model.workers.Manager;
import com.MainDriver.WorkFlowManager.model.workers.StandardWorker;
import com.MainDriver.WorkFlowManager.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class StandardWorkerService {

    private final AnnouncementRepository announcementRepository;
    private final ManagerRepository managerRepository;
    private final StandardWorkerRepository standardWorkerRepository;
    private final UserRepository userRepository;

    private final StandardWorkerAnnouncementRepository standardWorkerAnnouncementRepository;

    public StandardWorkerService(AnnouncementRepository announcementRepository, ManagerRepository managerRepository, StandardWorkerRepository standardWorkerRepository, UserRepository userRepository, StandardWorkerAnnouncementRepository standardWorkerAnnouncementRepository) {
        this.announcementRepository = announcementRepository;
        this.managerRepository = managerRepository;
        this.standardWorkerRepository = standardWorkerRepository;
        this.userRepository = userRepository;
        this.standardWorkerAnnouncementRepository = standardWorkerAnnouncementRepository;
    }

    @Transactional
    public Set<Announcement> getAllAnnouncements(StandardWorker standardWorker)
    {
        Set<Announcement> announcements = new HashSet<>();
        for(StandardWorkerAnnouncements SWA : standardWorkerAnnouncementRepository.findAllByStandardWorker(standardWorker)) {
            announcements.add(this.announcementRepository.getById(SWA.getAnnouncementID()));
        }

        return announcements;
    }

    public void removeAnnouncement( StandardWorker standardWorker, Long announcementID) {
        StandardWorkerAnnouncements standardWorkerAnnouncements = standardWorkerAnnouncementRepository.findByStandardWorkerAndAnnouncementID(standardWorker, announcementID);
        standardWorkerAnnouncementRepository.delete(standardWorkerAnnouncements);
    }

    public void addStandardWorker(Users user, StandardWorker standardWorker)
    {
        if((user !=null) &&(standardWorker != null))
        {
            standardWorker.setEmployeeRole(user.getRoles());
            standardWorker.setUserName(user.getUsername());

            Manager manager = managerRepository.findByUserName(standardWorker.getManagerUsername());
            if(manager != null) {
                standardWorker.setManager(manager);
            }
            standardWorkerRepository.save(standardWorker);
        }
    }

    public Set<StandardWorker> findAllByUsername(String username)
    {
        Set<StandardWorker> standardWorkerSet = new HashSet<>();
        for(Users p: this.userRepository.findAllByRolesAndUsernameLike("STANDARDWORKER", "%" + username + "%"))
        {
            standardWorkerSet.add(this.standardWorkerRepository.findByuserName(p.getUsername()));
        }
        return standardWorkerSet;
    }

    public StandardWorker getByUsername(String username)
    {
        StandardWorker standardWorker = this.standardWorkerRepository.findByuserName(username);
        if(standardWorker != null)
        {
            System.out.println("FOUND STANDARD WORKER: " + standardWorker.getUserName());
            return  standardWorker;
        }
        return new StandardWorker("NULL");
    }

    public void insertAlteredStandardWorker(StandardWorker standardWorker, String username)
    {
        StandardWorker modifyThis = this.standardWorkerRepository.findByuserName(username);
        if((standardWorker != null) && (modifyThis != null))
        {
            System.out.println("Over writing" +modifyThis.getFirstName() + " with: " +standardWorker.getFirstName() );
            modifyThis.setFirstName(standardWorker.getFirstName());

            System.out.println("Over writing" +modifyThis.getLastName() + " with: " + standardWorker.getLastName() );
            modifyThis.setLastName(standardWorker.getLastName());

            System.out.println("Over writing" +modifyThis.getRole() + " with: " + standardWorker.getRole() );
            modifyThis.setEmployeeRole(standardWorker.getRole());

            System.out.println("Over writing" +modifyThis.getManagerUsername() + " with: " + standardWorker.getManagerUsername() );
            modifyThis.setManagerUsername(standardWorker.getManagerUsername());


            this.standardWorkerRepository.save(modifyThis);
        }
    }
}
