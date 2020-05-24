package com.MainDriver.WorkFlowManager.Model.Workers;

import com.MainDriver.WorkFlowManager.Model.Announcements.Announcement;
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


    private String team; //needs to be a table

    //Would like to enforce a no-default constructor
    public StandardWorker()
    {
        this.user = null;
    }

    public StandardWorker(Users user) { this.user = user; }

    public String getTeam() { return team; }

    public void setTeam(String team) { this.team = team; }

    public Set<Announcement> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(Set<Announcement> announcements) {
        this.announcements = announcements;
    }

    @Override
   public String toString() {
    return "StandardWorker{" +
            "team='" + team + '\'' +
            '}';
   }

   @Override
   public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    StandardWorker that = (StandardWorker) o;
    return Objects.equals(team, that.team);
   }

   @Override
   public int hashCode() {
    return Objects.hash(team);
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
