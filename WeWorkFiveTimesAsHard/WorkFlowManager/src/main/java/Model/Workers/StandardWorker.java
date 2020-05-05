package Model.Workers;

import Model.Projects.WorkProject;
import Model.User.Users;
import javax.persistence.*;

/*
    Standard worker, not an admin, not a manager, a grunt assigned to a manager, and a project
 */
@Entity
public class StandardWorker extends WorkerTypes
{

    @ManyToOne
    private WorkProject project;                                //Assigned project


    @ManyToOne(fetch = FetchType.EAGER)
    private Manager manager;                                    //Assigned Manager


    /*Would like to enforce a no-default constructor*/
   public StandardWorker()
    {
        this.user = null;
        this.manager = null;
    }

    /*We want to use this one!*/
    public StandardWorker(Users user)
    {
        this.user = user;
        this.manager = null;
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
