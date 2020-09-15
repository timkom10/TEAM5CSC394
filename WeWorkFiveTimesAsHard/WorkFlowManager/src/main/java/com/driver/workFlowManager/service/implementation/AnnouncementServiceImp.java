package com.driver.workFlowManager.service.implementation;

import com.driver.workFlowManager.model.messaging.Announcement;
import com.driver.workFlowManager.model.workers.Admin;
import com.driver.workFlowManager.model.workers.Manager;
import com.driver.workFlowManager.model.workers.StandardWorker;
import com.driver.workFlowManager.repository.AdminRepository;
import com.driver.workFlowManager.repository.ManagerRepository;
import com.driver.workFlowManager.repository.StandardWorkerRepository;
import com.driver.workFlowManager.service.AnnouncementService;
import com.driver.workFlowManager.service.UserService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class AnnouncementServiceImp implements AnnouncementService {


    private final UserService userService;
    private final StandardWorkerRepository standardWorkerRepository;
    private final ManagerRepository managerRepository;
    private final AdminRepository adminRepository;

    public AnnouncementServiceImp(UserService userService, StandardWorkerRepository standardWorkerRepository, ManagerRepository managerRepository, AdminRepository adminRepository) {
        this.userService = userService;
        this.standardWorkerRepository = standardWorkerRepository;
        this.managerRepository = managerRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    @Transactional
    public List<Announcement> getAllAnnouncementsByUsername(String username) {
        if(this.standardWorkerRepository.existsByUserName(username)) {
            return standardWorkerRepository.findByUserName(username).getAnnouncements();
        }
        else if(this.managerRepository.existsByUserName(username)) {
            return this.managerRepository.findByUserName(username).getAnnouncements();
        }
        else if(this.adminRepository.existsByUserName(username)) {
            return this.adminRepository.findByUserName(username).getAnnouncements();
        }
        return new ArrayList<>();
    }

    @Override
    @Transactional
    public Announcement getByUsernameAndAnnouncementId(String username, Integer announcementId) {
        List<Announcement> announcements = this.getAllAnnouncementsByUsername(username);
        if(announcements != null) {
            for(Announcement a : announcements) {
                if(a.getId().equals(announcementId)) { return a; }
            }
        }
        return new Announcement();
    }

    @Override
    @Transactional
    public void sendAnnouncement(Announcement announcement, String from, String toManager)
    {
        if(!managerRepository.existsByUserName(toManager)) {return;}
        announcement.setTo(toManager);

        //ensure that from is a manager or an admin
        if(this.adminRepository.existsByUserName(from)) {
            announcement.setFrom(from);
            Admin admin = this.adminRepository.findByUserName(from);
            admin.addAnnouncement(announcement);
            this.adminRepository.save(admin);
        }
        else if(this.managerRepository.existsByUserName(from)) {
            announcement.setFrom(from);
        }
        if(announcement.getFrom().equals(from)) {
            //Already passed validation (!null)
            Manager manager = this.managerRepository.findByUserName(toManager);
            manager.addAnnouncement(announcement);
            this.managerRepository.save(manager);
            Set<StandardWorker> workers = manager.getDominion();
            for(StandardWorker w : workers) {
                w.getAnnouncements().add(announcement);
                this.standardWorkerRepository.save(w);
            }
        }
    }

    @Override
    @Transactional
    public void deleteAnnouncement(String username, Integer announcementID)
    {
        List<Announcement> announcements = this.getAllAnnouncementsByUsername(username);
        if(announcements != null) {
            for(Announcement a : announcements)
            {
                if(a.getId().equals(announcementID)) {
                    announcements.remove(a);
                    this.userService.simpleSaveUserInRoleRepo(username);
                    return;
                }
            }
        }
    }
}
