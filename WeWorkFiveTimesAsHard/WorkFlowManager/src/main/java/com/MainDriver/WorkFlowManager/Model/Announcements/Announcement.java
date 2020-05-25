package com.MainDriver.WorkFlowManager.Model.Announcements;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long A_ID;

    public String WrittenBy;
    public String Subject;

    @Lob
    public String MessageContent;

    public Announcement(){
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
