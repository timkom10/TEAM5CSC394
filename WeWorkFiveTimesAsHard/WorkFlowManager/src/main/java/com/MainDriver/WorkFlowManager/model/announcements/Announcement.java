package com.MainDriver.WorkFlowManager.model.announcements;

import com.MainDriver.WorkFlowManager.model.workers.Manager;
import com.MainDriver.WorkFlowManager.model.workers.StandardWorker;
import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@Table(name= "announcements", schema = "public")
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Manager manager;

    private String WrittenBy;
    private String Subject;

    @Lob
    private String MessageContent;

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
}
