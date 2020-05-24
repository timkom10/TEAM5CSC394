package com.MainDriver.WorkFlowManager.repository;

import com.MainDriver.WorkFlowManager.Model.Announcements.Announcement;
import org.springframework.data.repository.CrudRepository;

public interface AnnouncementRepository extends CrudRepository<Announcement, Long> {
}
