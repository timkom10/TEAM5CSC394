package com.MainDriver.WorkFlowManager.Model.Workers;

import com.MainDriver.WorkFlowManager.Model.User.Users;

import javax.persistence.*;
import java.util.Objects;

/*
    Standard worker, not an admin, not a manager, a grunt assigned to a manager, and a project
 */
@Entity
public class StandardWorker extends WorkerTypes
{

    private String team;

    //Would like to enforce a no-default constructor
    public StandardWorker()
    {
        this.user = null;
    }

    public StandardWorker(Users user) { this.user = user; }

    public String getTeam() { return team; }

    public void setTeam(String team) { this.team = team; }


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
