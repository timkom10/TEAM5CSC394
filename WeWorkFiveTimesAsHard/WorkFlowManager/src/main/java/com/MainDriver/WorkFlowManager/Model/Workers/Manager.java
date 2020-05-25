package com.MainDriver.WorkFlowManager.Model.Workers;

import com.MainDriver.WorkFlowManager.Model.Projects.Project;
import com.MainDriver.WorkFlowManager.Model.User.Users;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Manager extends WorkerTypes
{

    @OneToMany
    Set<StandardWorker> dominion = new HashSet<StandardWorker>();

    @OneToMany
    Set<Project> projects = new HashSet<Project>();

    public Manager() {
        this.user = null;
    }

    public Manager(Users user) {
        this.user = user;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Manager manager = (Manager) o;
        return dominion.equals(manager.dominion) &&
                projects.equals(manager.projects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dominion, projects);
    }

    @Override
    public String toString() {
        return "Manager{" +
                "dominion=" + dominion +
                ", projects=" + projects +
                ", user=" + user +
                '}';
    }
}
