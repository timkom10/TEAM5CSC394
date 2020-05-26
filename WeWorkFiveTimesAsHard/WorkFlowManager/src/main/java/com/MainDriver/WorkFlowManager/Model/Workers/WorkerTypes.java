package com.MainDriver.WorkFlowManager.Model.Workers;

/*
    Outlines the basic functionalities we would expect from a worker, regardless of type (admin, manager, standard)
 */

import javax.persistence.*;

@Entity
@Inheritance(strategy =  InheritanceType.TABLE_PER_CLASS)
public abstract class WorkerTypes
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long WorkerID;                         //Becomes the primary key of admin, manager, standard

    public abstract String getFirstName();
    public abstract String getLastName();
    public abstract String getHireDate();
    public abstract String getRole();

    public abstract String getPassword();
    public abstract String getUsername();
    public abstract void setPassword(String pass);
    public abstract void setUserName(String userName);

    public abstract void setFirstName(String name);
    public abstract void setLastName(String name);
    public abstract void setHireDate(String hireDate);
    public abstract void setRole(String role);

    public abstract void sendMessage();
    public abstract void receiveMessage();
    public abstract void viewProjects();
    public abstract void viewTasks();
}
