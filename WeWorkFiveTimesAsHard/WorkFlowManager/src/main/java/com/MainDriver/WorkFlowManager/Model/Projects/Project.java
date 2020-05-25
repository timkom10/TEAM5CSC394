package com.MainDriver.WorkFlowManager.Model.Projects;

import com.MainDriver.WorkFlowManager.Model.Workers.Manager;
import com.MainDriver.WorkFlowManager.Model.Workers.StandardWorker;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long PID;

    @OneToMany
    private Set<StandardWorker> teamMembers = new HashSet<StandardWorker>();

    @OneToMany
    private Set<Tasks> tasks = new HashSet<Tasks>();

    @ManyToOne
    private Manager manager;

    private String ProjectName;

    public Project(){
    }


    public Project(Manager manager)
    {
        this.manager = manager;
    }

    public String getProjectName() {
        return ProjectName;
    }

    public void setProjectName(String projectName) {
        ProjectName = projectName;
    }

    public Set<StandardWorker> getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(Set<StandardWorker> teamMembers) {
        this.teamMembers = teamMembers;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }
}
