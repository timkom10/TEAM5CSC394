package com.MainDriver.WorkFlowManager.Model.Workers;

import com.MainDriver.WorkFlowManager.Model.User.Users;

import javax.persistence.Entity;

@Entity
public class Manager extends WorkerTypes{

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
