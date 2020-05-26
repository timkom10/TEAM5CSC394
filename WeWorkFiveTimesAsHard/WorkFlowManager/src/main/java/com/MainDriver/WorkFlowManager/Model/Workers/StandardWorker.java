package com.MainDriver.WorkFlowManager.Model.Workers;

import com.MainDriver.WorkFlowManager.Model.Announcements.Announcement;
import com.MainDriver.WorkFlowManager.Model.Feedback.Feedback;
import com.MainDriver.WorkFlowManager.Model.Projects.Project;
import com.MainDriver.WorkFlowManager.Model.Projects.Tasks;


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

    @OneToMany(mappedBy = "standardWorker", orphanRemoval = true,
            fetch = FetchType.EAGER)
    private Set<Announcement> announcements = new HashSet<Announcement>();

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "standardWorker", orphanRemoval = true)
    private Set<Tasks> currentTasks = new HashSet<Tasks>();

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "standardWorker")
    private Set<Feedback> feedbacks = new HashSet<Feedback>();

    @ManyToOne
    private Project project;

    @ManyToOne
    private Manager manager;

    private String userName;
    private String password;

    private String firstName;
    private String lastName;
    private String hireDate;
    private String role;

    private int points = 0;

    public StandardWorker() {
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
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
    public String getFirstName() {
        return this.firstName;
    }

    @Override
    public String getLastName() {
        return this.lastName;
    }

    @Override
    public String getHireDate() {
        return this.hireDate;
    }

    @Override
    public String getRole() {
        return this.role;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUserName() {
        return this.userName;
    }

    @Override
    public void setPassword(String pass) {
        this.password =pass;
    }

    @Override
    public void setUserName(String userName) {
            this.userName =userName;
    }

    @Override
    public void setFirstName(String name) {
        this.firstName = name;
    }

    @Override
    public void setLastName(String name) {
        this.lastName = name;
    }

    @Override
    public void setHireDate(String hireDate) {
        this.hireDate =hireDate;
    }

    @Override
    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public void sendMessage() {
    }

    @Override
    public void receiveMessage() {
    }

    @Override
    public void viewProjects() {
    }

    @Override
    public void viewTasks() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StandardWorker that = (StandardWorker) o;
        return points == that.points &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(hireDate, that.hireDate) &&
                Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, hireDate, role, points);
    }

    @Override
    public String toString() {
        return "StandardWorker{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", hireDate='" + hireDate + '\'' +
                ", role='" + role + '\'' +
                ", points=" + points +
                '}';
    }
}
