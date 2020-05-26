package com.MainDriver.WorkFlowManager.Model.Workers;

import com.MainDriver.WorkFlowManager.Model.Announcements.Announcement;
import com.MainDriver.WorkFlowManager.Model.Feedback.Feedback;
import com.MainDriver.WorkFlowManager.Model.Projects.Project;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Manager extends WorkerTypes {

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "manager", orphanRemoval = true)
    private Set<StandardWorker> dominion = new HashSet<StandardWorker>();

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "manager")
    private Set<Project> projects = new HashSet<Project>();

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "manager")
    private Set<Announcement> announcements = new HashSet<Announcement>();

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "manager")
    private Set<Feedback> feedbacks = new HashSet<Feedback>();

    private String userName;
    private String password;

    private String firstName;
    private String lastName;
    private String hireDate;
    private String role;

    public Manager() {
    }

    public Set<StandardWorker> getDominion() {
        return dominion;
    }

    public void setDominion(Set<StandardWorker> dominion) {
        this.dominion = dominion;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
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
        Manager manager = (Manager) o;
        return Objects.equals(firstName, manager.firstName) &&
                Objects.equals(lastName, manager.lastName) &&
                Objects.equals(hireDate, manager.hireDate) &&
                Objects.equals(role, manager.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, hireDate, role);
    }

    @Override
    public String toString() {
        return "Manager{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", hireDate='" + hireDate + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
