package com.MainDriver.WorkFlowManager.Model.Projects;

import com.MainDriver.WorkFlowManager.Model.Workers.Manager;
import com.MainDriver.WorkFlowManager.Model.Workers.StandardWorker;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long PID;

    @OneToMany
    private Set<StandardWorker> teamMembers = new HashSet<StandardWorker>();

    @OneToOne
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(teamMembers, project.teamMembers) &&
                Objects.equals(manager, project.manager);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamMembers, manager);
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
