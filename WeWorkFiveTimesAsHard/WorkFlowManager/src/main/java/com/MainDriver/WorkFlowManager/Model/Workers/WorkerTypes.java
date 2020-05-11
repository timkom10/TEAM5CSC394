package com.MainDriver.WorkFlowManager.Model.Workers;

/*
    Outlines the basic functionalities we would expect from a worker, regardless of type (admin, manager, standard)
 */

import com.MainDriver.WorkFlowManager.Model.User.Users;
import javax.persistence.*;

@Entity
@Inheritance(strategy =  InheritanceType.TABLE_PER_CLASS)
public abstract class WorkerTypes
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long WorkerID;                                 //Becomes the primary key of admin, manager, standard

    @OneToOne
    public Users user;                                        //Any worker type becomes bound to this user

    public abstract void sendMessage(Users user);
    public abstract void receiveMessage(Users user);
    public abstract void viewProjects(Users user);
    public abstract void viewTasks();
}
