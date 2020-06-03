package com.MainDriver.WorkFlowManager.model.feedback;

import com.MainDriver.WorkFlowManager.model.projects.Project;
import com.MainDriver.WorkFlowManager.model.projects.Tasks;
import com.MainDriver.WorkFlowManager.model.workers.Manager;
import com.MainDriver.WorkFlowManager.model.workers.StandardWorker;
import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@Table(name = "feedback", schema = "public")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Manager manager;

    @ManyToOne
    private StandardWorker standardWorker;

    @ManyToOne
    private Project project;

    @ManyToOne
    private Tasks tasks;

    @Lob
    private String feedbackMessage;

    private String writtenBy;

    private boolean isPublic;

    public Feedback() {
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return isPublic == feedback.isPublic &&
                Objects.equals(id, feedback.id) &&
                Objects.equals(feedbackMessage, feedback.feedbackMessage) &&
                Objects.equals(writtenBy, feedback.writtenBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, feedbackMessage, writtenBy, isPublic);
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", feedbackMessage='" + feedbackMessage + '\'' +
                ", writtenBy='" + writtenBy + '\'' +
                ", isPublic=" + isPublic +
                '}';
    }
}
