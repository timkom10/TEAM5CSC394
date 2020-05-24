package com.MainDriver.WorkFlowManager.Model.Workers;

import com.MainDriver.WorkFlowManager.Model.Announcements.Announcement;
import com.MainDriver.WorkFlowManager.Model.Projects.Project;
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

    @ManyToOne
    Project project;


    //Would like to enforce a no-default constructor
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
