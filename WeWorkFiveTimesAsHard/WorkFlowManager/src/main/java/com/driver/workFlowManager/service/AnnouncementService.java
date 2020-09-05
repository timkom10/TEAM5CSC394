package com.driver.workFlowManager.service;

import com.driver.workFlowManager.model.messaging.Announcement;
import java.util.List;

public interface AnnouncementService {
    List<Announcement> getAllAnnouncementsByUsername(String username);
    Announcement getByUsernameAndAnnouncementId(String username, Integer announcementId);
    void sendAnnouncement(Announcement announcement,String from, String toManager);
    void deleteAnnouncement(String username, Integer announcementID);
}
