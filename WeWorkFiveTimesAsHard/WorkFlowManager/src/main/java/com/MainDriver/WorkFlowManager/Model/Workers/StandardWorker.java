package com.MainDriver.WorkFlowManager.Model.Workers;

import com.MainDriver.WorkFlowManager.Model.User.Users;

import javax.persistence.*;

/*
    Standard worker, not an admin, not a manager, a grunt assigned to a manager, and a project
 */
@Entity
public class StandardWorker extends WorkerTypes
{


    /*Would like to enforce a no-default constructor*/
   public StandardWorker()
    {
        this.user = null;
    }

    /*We want to use this one!*/
    public StandardWorker(Users user)
    {
        this.user = user;
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
