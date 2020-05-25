package com.MainDriver.WorkFlowManager.Model.Feedback;

import com.MainDriver.WorkFlowManager.Model.Projects.Project;
import com.MainDriver.WorkFlowManager.Model.Projects.Tasks;
import com.MainDriver.WorkFlowManager.Model.Workers.Manager;
import com.MainDriver.WorkFlowManager.Model.Workers.StandardWorker;
import com.MainDriver.WorkFlowManager.Model.Workers.WorkerTypes;

import javax.persistence.*;

@Entity
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long F_id;

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

    public Feedback(WorkerTypes workerTypes) {
        this.writtenBy = workerTypes.getFirstName() + workerTypes.getLastName();
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

    public Tasks getTasks() {
        return tasks;
    }

    public void setTasks(Tasks tasks) {
        this.tasks = tasks;
    }

    public String getFeedbackMessage() {
        return feedbackMessage;
    }

    public void setFeedbackMessage(String feedbackMessage) {
        this.feedbackMessage = feedbackMessage;
    }

    public String getWrittenBy() {
        return writtenBy;
    }

    public void setWrittenBy(String writtenBy) {
        this.writtenBy = writtenBy;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }
}
