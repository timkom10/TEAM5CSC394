package com.MainDriver.WorkFlowManager.repository;

import com.MainDriver.WorkFlowManager.model.announcements.Announcement;
import org.springframework.data.repository.CrudRepository;

public interface AnnouncementRepository extends CrudRepository<Announcement, Long> {
    Announcement getById(Long id);
}
