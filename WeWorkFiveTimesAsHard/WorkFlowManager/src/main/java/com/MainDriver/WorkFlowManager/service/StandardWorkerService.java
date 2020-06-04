package com.MainDriver.WorkFlowManager.service;

import com.MainDriver.WorkFlowManager.model.announcements.Announcement;
import com.MainDriver.WorkFlowManager.model.announcements.StandardWorkerAnnouncements;
import com.MainDriver.WorkFlowManager.model.workers.StandardWorker;
import com.MainDriver.WorkFlowManager.repository.AnnouncementRepository;
import com.MainDriver.WorkFlowManager.repository.ManagerRepository;
import com.MainDriver.WorkFlowManager.repository.StandardWorkerAnnouncementRepository;
import com.MainDriver.WorkFlowManager.repository.StandardWorkerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class StandardWorkerService {


    private final AnnouncementRepository announcementRepository;
    private final ManagerRepository managerRepository;
    private final StandardWorkerRepository standardWorkerRepository;

    private final StandardWorkerAnnouncementRepository standardWorkerAnnouncementRepository;

    public StandardWorkerService(AnnouncementRepository announcementRepository, ManagerRepository managerRepository, StandardWorkerRepository standardWorkerRepository, StandardWorkerAnnouncementRepository standardWorkerAnnouncementRepository) {
        this.announcementRepository = announcementRepository;
        this.managerRepository = managerRepository;
        this.standardWorkerRepository = standardWorkerRepository;
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
        System.out.println("Count: " + standardWorkerAnnouncementRepository.count());

    }

    public void addStandardWorker(StandardWorker standardWorker)
    {
        standardWorker.setManager(managerRepository.findByUserName(standardWorker.getManagerUsername()));
        standardWorkerRepository.save(standardWorker);
    }
}
