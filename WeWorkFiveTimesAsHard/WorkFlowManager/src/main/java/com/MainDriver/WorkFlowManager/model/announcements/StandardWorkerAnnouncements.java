package com.MainDriver.WorkFlowManager.model.announcements;

import com.MainDriver.WorkFlowManager.model.workers.StandardWorker;

import javax.persistence.*;

@Entity
@Table(name= "standard_worker_announcements", schema = "public")
public class StandardWorkerAnnouncements {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    StandardWorker standardWorker;

    @Column(name = "AnnouncementId")
    Long announcementID;

    public StandardWorkerAnnouncements() {
    }

    public StandardWorkerAnnouncements(StandardWorker standardWorker, Long announcementID)
    {
        this.standardWorker =standardWorker;
        this.announcementID =announcementID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StandardWorker getStandardWorker() {
        return standardWorker;
    }

    public void setStandardWorker(StandardWorker standardWorker) {
        this.standardWorker = standardWorker;
    }

    public Long getAnnouncementID() {
        return announcementID;
    }

    public void setAnnouncementID(Long announcementID) {
        this.announcementID = announcementID;
    }
}
