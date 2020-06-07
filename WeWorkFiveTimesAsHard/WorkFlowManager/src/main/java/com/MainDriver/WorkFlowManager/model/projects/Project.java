package com.MainDriver.WorkFlowManager.model.projects;

import com.MainDriver.WorkFlowManager.model.workers.Manager;
import com.MainDriver.WorkFlowManager.model.workers.StandardWorker;
import lombok.Data;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name= "projects", schema = "public")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project", orphanRemoval = true)
    private Set<StandardWorker> teamMembers = new HashSet<StandardWorker>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project", orphanRemoval = true)
    private Set<Tasks> tasks = new HashSet<Tasks>();

    @ManyToOne
    private Manager manager;

    @Column(name="project_name")
    private String ProjectName;

    public Project(){
    }

    public Project(Manager manager)
    {
        this.manager = manager;
    }




}
