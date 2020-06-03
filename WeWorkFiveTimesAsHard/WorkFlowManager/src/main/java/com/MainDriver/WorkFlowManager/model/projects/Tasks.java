package com.MainDriver.WorkFlowManager.model.projects;

import com.MainDriver.WorkFlowManager.model.workers.Manager;
import com.MainDriver.WorkFlowManager.model.workers.StandardWorker;
import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@Table(name ="taks", schema = "public")
public class Tasks {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Manager manager;

    @ManyToOne
    private StandardWorker standardWorker;

    @ManyToOne
    private Project project;

    @Lob
    private String taskDescription;

    private String taskName;
    private final int bounty;
    private boolean complete = false;
    private boolean upForReview = false;

    public Tasks() {
        this.bounty = 0;
    }

    public Tasks(Manager manager, int bounty) {
        this.manager = manager;
        this.bounty =bounty;
    }

    public void markComplete(Manager manager) {
        if(manager == this.manager) {
            this.complete = true;
        }
    }

    public void markUpForReview(StandardWorker worker) {
        if(worker == this.standardWorker) {
            this.upForReview = true;
        }
    }

    public void ResetUpForReview(Manager manager, boolean bool) {
        if(this.manager == manager) {
            this.upForReview = bool;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tasks tasks = (Tasks) o;
        return bounty == tasks.bounty &&
                complete == tasks.complete &&
                upForReview == tasks.upForReview &&
                Objects.equals(id, tasks.id) &&
                Objects.equals(taskDescription, tasks.taskDescription) &&
                Objects.equals(taskName, tasks.taskName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, taskDescription, taskName, bounty, complete, upForReview);
    }

    @Override
    public String toString() {
        return "Tasks{" +
                "id=" + id +
                ", taskDescription='" + taskDescription + '\'' +
                ", taskName='" + taskName + '\'' +
                ", bounty=" + bounty +
                ", complete=" + complete +
                ", upForReview=" + upForReview +
                '}';
    }
}
