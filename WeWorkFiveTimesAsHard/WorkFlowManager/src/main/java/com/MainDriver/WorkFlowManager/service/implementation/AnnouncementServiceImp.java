package com.MainDriver.WorkFlowManager.service.implementation;

import com.MainDriver.WorkFlowManager.model.messaging.Announcement;
import com.MainDriver.WorkFlowManager.model.messaging.Message;
import com.MainDriver.WorkFlowManager.model.workers.Manager;
import com.MainDriver.WorkFlowManager.model.workers.StandardWorker;
import com.MainDriver.WorkFlowManager.repository.AdminRepository;
import com.MainDriver.WorkFlowManager.repository.ManagerRepository;
import com.MainDriver.WorkFlowManager.repository.StandardWorkerRepository;
import com.MainDriver.WorkFlowManager.service.AnnouncementService;
import com.MainDriver.WorkFlowManager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class AnnouncementServiceImp implements AnnouncementService {

    @Autowired
    UserService userService;

    private final StandardWorkerRepository standardWorkerRepository;
    private final ManagerRepository managerRepository;
    private final AdminRepository adminRepository;

    public AnnouncementServiceImp(StandardWorkerRepository standardWorkerRepository, ManagerRepository managerRepository, AdminRepository adminRepository) {
        this.standardWorkerRepository = standardWorkerRepository;
        this.managerRepository = managerRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public List<Announcement> getAllAnnouncementsByUsername(String username) {
        if(this.standardWorkerRepository.existsByUserName(username)) {
            return standardWorkerRepository.findByuserName(username).getAnnouncements();
        }
        else if(this.managerRepository.existsByUserName(username)) {
            return this.managerRepository.findByUserName(username).getAnnouncements();
        }
        else if(this.adminRepository.existsByUserName(username)) {
            return this.adminRepository.findByUserName(username).getAnnouncements();
        }
        return new ArrayList<Announcement>();
    }

    @Override
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
    public void sendAnnouncement(Announcement announcement, String from, String toManager)
    {
        if(this.managerRepository.existsByUserName(toManager) == false) {return;}
        announcement.setTo(toManager);

        //ensure that from is a manager or an admin
        if(this.adminRepository.existsByUserName(from)) {
            announcement.setFrom(from);
        }
        else if(this.managerRepository.existsByUserName(from)) {
            announcement.setFrom(from);
        }
        if(announcement.getFrom().equals(from))
        {
            //passed validation
            Manager manager = this.managerRepository.findByUserName(from);
            Set<StandardWorker> workers = manager.getDominion();
            for(StandardWorker w : workers) {
                w.getAnnouncements().add(announcement);
                this.standardWorkerRepository.save(w);
            }
        }
    }

    @Override
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
