package com.MainDriver.WorkFlowManager.Model.Announcements;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long A_ID;

    public String WrittenBy;
    public String Subject;
    public String MessageContent;

    public Announcement(){

    }

    public String getWrittenBy() {
        return WrittenBy;
    }

    public void setWrittenBy(String writtenBy) {
        WrittenBy = writtenBy;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getMessageContent() {
        return MessageContent;
    }

    public void setMessageContent(String messageContent) {
        MessageContent = messageContent;
    }
}
