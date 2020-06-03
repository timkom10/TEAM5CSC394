package com.MainDriver.WorkFlowManager.repository;

import com.MainDriver.WorkFlowManager.model.announcements.StandardWorkerAnnouncements;
import com.MainDriver.WorkFlowManager.model.workers.StandardWorker;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface StandardWorkerAnnouncementRepository extends CrudRepository<StandardWorkerAnnouncements, Long> {
    Set<StandardWorkerAnnouncements> findAllByStandardWorker(StandardWorker standardWorker);
    void removeByStandardWorkerAndAnnouncementID(StandardWorker standard, Long id);
    StandardWorkerAnnouncements findByStandardWorkerAndAnnouncementID(StandardWorker standard, Long id);
}
