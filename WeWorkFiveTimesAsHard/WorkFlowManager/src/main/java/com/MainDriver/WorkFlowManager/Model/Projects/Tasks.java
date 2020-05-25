package com.MainDriver.WorkFlowManager.Model.Projects;

import com.MainDriver.WorkFlowManager.Model.Workers.Manager;
import com.MainDriver.WorkFlowManager.Model.Workers.StandardWorker;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Tasks {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long T_ID;

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

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
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

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public int getBounty() {
        return bounty;
    }

    public boolean isComplete() {
        return complete;
    }

    public boolean isUpForReview() {
        return upForReview;
    }

    @Override
    public String toString() {
        return "Tasks{" +
                "taskDescription='" + taskDescription + '\'' +
                ", taskName='" + taskName + '\'' +
                ", bounty=" + bounty +
                ", complete=" + complete +
                ", upForReview=" + upForReview +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tasks tasks = (Tasks) o;
        return bounty == tasks.bounty &&
                complete == tasks.complete &&
                upForReview == tasks.upForReview &&
                Objects.equals(manager, tasks.manager) &&
                Objects.equals(standardWorker, tasks.standardWorker) &&
                Objects.equals(project, tasks.project) &&
                Objects.equals(taskDescription, tasks.taskDescription) &&
                Objects.equals(taskName, tasks.taskName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(manager, standardWorker, project, taskDescription, taskName, bounty, complete, upForReview);
    }
}
