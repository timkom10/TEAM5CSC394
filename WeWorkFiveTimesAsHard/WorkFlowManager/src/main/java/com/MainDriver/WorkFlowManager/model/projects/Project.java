package com.MainDriver.WorkFlowManager.model.projects;

import com.MainDriver.WorkFlowManager.model.messaging.Message;
import com.MainDriver.WorkFlowManager.model.workers.Manager;
import com.MainDriver.WorkFlowManager.model.workers.StandardWorker;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import lombok.Getter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;


import javax.persistence.*;
import java.util.*;

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

    @Type( type = "jsonb" )
    @Column( columnDefinition = "jsonb", name ="recently_completed_tasks" )
    @Basic(fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Task> completedTasks = new ArrayList<Task>();

    @Column(name = "next_recent_task_key")
    private int completedTasksSize = 0;

    @Type( type = "jsonb" )
    @Column( columnDefinition = "jsonb", name ="all_tasks" )
    @Basic(fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Task> tasks = new ArrayList<Task>();

    @Column(name = "next_task_key")
    private Integer nextTaskKey = 0;

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

    public List<Task> getCompletedTasksReverse()
    {
        List<Task> reverseList = this.completedTasks;
        Collections.reverse(reverseList);
        return reverseList;
    }

    public void addCompletedTask(Task task) {

        if(task != null && task.isComplete()) {
            this.completedTasks.add(task);
            this.completedTasksSize++;
        }
        if(completedTasksSize > 5) {
            this.completedTasks.remove(0);
            completedTasksSize--;
        }
    }

    public void addTask(Task task) {
        if(task != null) {
            task.setProjectId(this.id);
            task.setTaskId(this.nextTaskKey++);
            this.tasks.add(task);
        }
    }
}
