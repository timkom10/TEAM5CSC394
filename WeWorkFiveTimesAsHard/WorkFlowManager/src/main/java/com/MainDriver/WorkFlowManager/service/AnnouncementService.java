package com.MainDriver.WorkFlowManager.service;

import com.MainDriver.WorkFlowManager.model.messaging.Announcement;
import com.MainDriver.WorkFlowManager.model.messaging.Message;

import java.util.List;

public interface AnnouncementService {
    List<Announcement> getAllAnnouncementsByUsername(String username);
    Announcement getByUsernameAndAnnouncementId(String username, Integer announcementId);
    void sendAnnouncement(Announcement announcement,String from, String toManager);
    void deleteAnnouncement(String username, Integer announcementID);
}
