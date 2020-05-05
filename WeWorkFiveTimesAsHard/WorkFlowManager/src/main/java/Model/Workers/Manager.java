package Model.Workers;

import Model.Projects.WorkProject;
import Model.User.Users;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Manager extends WorkerTypes
{
    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<WorkProject> projects;                     //Managed projects

    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<StandardWorker> teamMembers;               //Managed team members


    public Manager()
    {
        this.user = null;
        this.projects = null;
        this.teamMembers = null;
    }

    public Manager(Users user)
    {
        this.user = user;
        this.projects = null;
        this.teamMembers = null;
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
