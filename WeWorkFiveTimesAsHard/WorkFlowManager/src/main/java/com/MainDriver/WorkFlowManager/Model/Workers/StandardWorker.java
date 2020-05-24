package com.MainDriver.WorkFlowManager.Model.Workers;

import com.MainDriver.WorkFlowManager.Model.Announcements.Announcement;
import com.MainDriver.WorkFlowManager.Model.Projects.Project;
import com.MainDriver.WorkFlowManager.Model.Projects.Tasks;
import com.MainDriver.WorkFlowManager.Model.User.Users;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/*
    Standard worker, not an admin, not a manager, a grunt assigned to a manager, and a project
 */
@Entity
public class StandardWorker extends WorkerTypes
{

    @OneToMany
    Set<Announcement> announcements = new HashSet<Announcement>();

    @OneToMany
    Set<Tasks> currentTasks = new HashSet<Tasks>();

    @ManyToOne
    Project project;

    private int points = 0;

    public StandardWorker() {
        this.user = null;
    }

    public StandardWorker(Users user) {
        this.user = user;
    }

    public Set<Announcement> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(Set<Announcement> announcements) {
        this.announcements = announcements;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void updatePoints(Tasks completedTask, int addScore) {
        if(completedTask.isComplete() && (completedTask.getStandardWorker() == this)) {
            this.points += addScore;
        }
    }

    public Set<Tasks> getCurrentTasks() {
        return currentTasks;
    }

    public void setCurrentTasks(Set<Tasks> currentTasks) {
        this.currentTasks = currentTasks;
    }

    @Override
    public String toString() {
        return "StandardWorker{" +
                "announcements=" + announcements +
                ", currentTasks=" + currentTasks +
                ", project=" + project +
                ", points=" + points +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StandardWorker that = (StandardWorker) o;
        return points == that.points &&
                Objects.equals(announcements, that.announcements) &&
                Objects.equals(currentTasks, that.currentTasks) &&
                Objects.equals(project, that.project);
    }

    @Override
    public int hashCode() {
        return Objects.hash(announcements, currentTasks, project, points);
    }

    @Override
    public void sendMessage(Users user) {
    }

    @Override
    public void receiveMessage(Users user) {
    }

    @Override
    public void viewProjects(Users user) {
    }

    @Override
    public void viewTasks() {
    }
}
