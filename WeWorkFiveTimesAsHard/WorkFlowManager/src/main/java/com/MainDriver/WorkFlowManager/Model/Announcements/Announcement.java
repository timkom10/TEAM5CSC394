package com.MainDriver.WorkFlowManager.Model.Announcements;

import com.MainDriver.WorkFlowManager.Model.Workers.Manager;
import com.MainDriver.WorkFlowManager.Model.Workers.StandardWorker;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long A_ID;

    @ManyToOne
    private Manager manager;

    @ManyToOne
    private StandardWorker standardWorker;


    private String WrittenBy;
    private String Subject;

    @Lob
    private byte[] MessageContent;

    public Announcement(){
    }

    public Announcement(Manager manager) {
        this.manager =manager;
    }

    @Override
    public String toString() {
        return "Announcement{" +
                "WrittenBy='" + WrittenBy + '\'' +
                ", Subject='" + Subject + '\'' +
                ", MessageContent='" + MessageContent + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Announcement that = (Announcement) o;
        return Objects.equals(WrittenBy, that.WrittenBy) &&
                Objects.equals(Subject, that.Subject) &&
                Objects.equals(MessageContent, that.MessageContent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(WrittenBy, Subject, MessageContent);
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public StandardWorker getStandardWorker() {
        return standardWorker;
    }

    public void setStandardWorker(StandardWorker standardWorker) {
        this.standardWorker = standardWorker;
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
        return new String(this.MessageContent);
    }

    public void setMessageContent(String messageContent) {
        this.MessageContent = messageContent.getBytes();
    }
}
