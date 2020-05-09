package com.MainDriver.WorkFlowManager.Model.Projects;

import com.MainDriver.WorkFlowManager.Model.Workers.Manager;
import com.MainDriver.WorkFlowManager.Model.Workers.StandardWorker;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/*
    A work project is, something assigned by a manager to many employees
 */
@Entity
public class WorkProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int projectID;                              //Primary key

    @ManyToOne
    private Manager manager;                            //project owner, Key

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<StandardWorker> projectWorkers;        //team-members of the project


    /*Try to force no-default*/
    public WorkProject()
    {
        this.manager =null;
        this.projectWorkers =null;
    }

    public WorkProject(Manager manager)
    {
        this.manager = manager;
        this.projectWorkers = null;
    }

    public void addWorkers(Manager man, StandardWorker worker)
    {
        /*Ensure that it is the manager of the project assigning the workers*/
        if(this.manager == man)
        {
            if(this.projectWorkers == null)
            {
                this.projectWorkers = new ArrayList<StandardWorker>();
            }
            this.projectWorkers.add(worker);
        }
    }

    public void removeWorker(Manager man, StandardWorker worker)
    {
        if(this.manager == man)
        {
            if(this.projectWorkers != null)
            {
                this.projectWorkers.remove(worker);
            }
        }
    }

}
