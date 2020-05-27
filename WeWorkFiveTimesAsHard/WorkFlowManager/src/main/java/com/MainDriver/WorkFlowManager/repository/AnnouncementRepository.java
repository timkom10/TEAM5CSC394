package com.MainDriver.WorkFlowManager.repository;

import com.MainDriver.WorkFlowManager.Model.Announcements.Announcement;
import com.MainDriver.WorkFlowManager.Model.Workers.StandardWorker;
import com.sun.xml.bind.api.impl.NameConverter;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface AnnouncementRepository extends CrudRepository<Announcement, Long> {
}
