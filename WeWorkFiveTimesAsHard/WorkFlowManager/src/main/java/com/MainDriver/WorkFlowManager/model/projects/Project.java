package com.MainDriver.WorkFlowManager.model.projects;

import com.MainDriver.WorkFlowManager.model.messaging.Message;
import com.MainDriver.WorkFlowManager.model.workers.Manager;
import com.MainDriver.WorkFlowManager.model.workers.StandardWorker;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})

@Entity
@Data
@Table(name= "projects", schema = "public")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project", orphanRemoval = true)
    private Set<StandardWorker> teamMembers = new HashSet<StandardWorker>();

    @ManyToOne
    private Manager manager;

    @Column(name="project_name")
    private String ProjectName;


    @Type( type = "jsonb" )
    @Column( columnDefinition = "jsonb", name ="milestones" )
    @Basic(fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Milestones> milestones = new ArrayList<Milestones>();

    @Column(name = "next_milestone_key")
    private Integer nextMileStoneKey = 0;

    @Column(name ="description")
    @Lob
    String projectDescription;

    public Project(){
    }
    public Project(Manager manager) {
        this.manager = manager;
    }

    public void addMilestone(Milestones milestone) {
        if(milestone != null) {
            milestone.setProjectId(this.id);
            milestone.setId(this.nextMileStoneKey++);
            this.milestones.add(milestone);
        }
    }

}
