package com.MainDriver.WorkFlowManager.Model.Workers;

import com.MainDriver.WorkFlowManager.Model.Projects.Project;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Manager extends WorkerTypes
{

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "manager", orphanRemoval = true)
    Set<StandardWorker> dominion = new HashSet<StandardWorker>();

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "manager")
    Set<Project> projects = new HashSet<Project>();

    public String firstName;
    public String lastName;
    public String hireDate;
    public String role;

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
