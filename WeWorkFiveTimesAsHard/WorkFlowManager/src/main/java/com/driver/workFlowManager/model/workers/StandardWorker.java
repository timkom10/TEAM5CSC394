package com.driver.workFlowManager.model.workers;

import com.driver.workFlowManager.model.feedback.Feedback;
import com.driver.workFlowManager.model.messaging.Announcement;
import com.driver.workFlowManager.model.messaging.Message;
import com.driver.workFlowManager.model.projects.Project;
import com.driver.workFlowManager.model.projects.Task;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Data
@Table(name= "standard_worker", schema = "public")

@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@AllArgsConstructor
@NoArgsConstructor
public class StandardWorker extends WorkerType implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Project project;

    @ManyToOne()
    private Manager manager;

    private String userName;
    private String firstName;
    private String lastName;
    private String hireDate;
    private String employeeRole;
    private int totalPoints = 0;
    private int currentProjectPoints = 0;

    @Type( type = "jsonb" )
    @Column( columnDefinition = "jsonb", name ="messages" )
    @Basic(fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Message> messages = new ArrayList<>();

    @Column(name = "last_message_id")
    private Integer lastMessageKey = 0;

    @Type( type = "jsonb" )
    @Column( columnDefinition = "jsonb", name ="announcements" )
    @Basic(fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Announcement> announcements = new ArrayList<>();

    @Column(name = "last_announcement_id")
    private Integer lastAnnouncementKey = 0;

    @Type( type = "jsonb" )
    @Column( columnDefinition = "jsonb", name ="personal_feedback" )
    @Basic(fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Feedback> personalFeedback = new ArrayList<>();

    @Column(name = "last_feedback_id")
    private Integer lastFeedbackKey = 0;

    public void addMessage(Message message) {
        if(message != null) {
            message.setId(this.lastMessageKey++);
            this.messages.add(message);
        }
    }

    public void addAnnouncement(Announcement announcement) {
        if(announcement != null) {
            announcement.setId(lastAnnouncementKey++);
            this.announcements.add(announcement);
        }
    }

    public void addFeedback(Feedback feedback) {
        if(feedback != null) {
            feedback.setId(this.lastFeedbackKey++);
            this.personalFeedback.add(feedback);
        }
    }

    public void didTask(Task task)
    {
        if(task != null && task.getIsComplete() > 0) {
            this.currentProjectPoints += task.getBounty();
            this.totalPoints += task.getBounty();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StandardWorker that = (StandardWorker) o;
        return totalPoints == that.totalPoints &&
                currentProjectPoints == that.currentProjectPoints &&
                Objects.equals(id, that.id) &&
                Objects.equals(userName, that.userName) &&
                Objects.equals(employeeRole, that.employeeRole) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(hireDate, that.hireDate) &&
                Objects.equals(lastMessageKey, that.lastMessageKey) &&
                Objects.equals(lastAnnouncementKey, that.lastAnnouncementKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, employeeRole, firstName, lastName, hireDate, totalPoints, currentProjectPoints, lastMessageKey, lastAnnouncementKey);
    }

    @Override
    public String toString() {
        return "StandardWorker{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", employeeRole='" + employeeRole + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", hireDate='" + hireDate + '\'' +
                ", totalPoints=" + totalPoints +
                ", currentProjectPoints=" + currentProjectPoints +
                ", lastMessageKey=" + lastMessageKey +
                ", lastAnnouncementKey=" + lastAnnouncementKey +
                '}';
    }

    @Override
    public String getWorkerTypeUsername() {
        return  this.userName;
    }

    @Override
    public String getRole() {
        return this.employeeRole;
    }
}
