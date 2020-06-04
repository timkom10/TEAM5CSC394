package com.MainDriver.WorkFlowManager.model.workers;

import com.MainDriver.WorkFlowManager.model.announcements.Announcement;
import com.MainDriver.WorkFlowManager.model.announcements.StandardWorkerAnnouncements;
import com.MainDriver.WorkFlowManager.model.feedback.Feedback;
import com.MainDriver.WorkFlowManager.model.projects.Project;
import com.MainDriver.WorkFlowManager.model.projects.Tasks;
import lombok.Data;

import javax.persistence.*;
import java.util.*;

/*
    Standard worker, not an admin, not a manager, a grunt assigned to a manager, and a project
 */
@Entity
@Data
@Table(name= "standard_worker", schema = "public")
public class StandardWorker extends WorkerType
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "standardWorker", orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<StandardWorkerAnnouncements> standardWorkerAnnouncements = new HashSet<StandardWorkerAnnouncements>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "standardWorker", orphanRemoval = true)
    private Set<Tasks> currentTasks = new HashSet<Tasks>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "standardWorker")
    private Set<Feedback> feedbacks = new HashSet<Feedback>();

    @ManyToOne
    private Project project;

    @ManyToOne()
    private Manager manager;

    private String managerUsername;
    private String userName;
    private String employeeRole;
    private String firstName;
    private String lastName;
    private String hireDate;
    private int points = 0;

    public StandardWorker() {
    }

    public StandardWorker(String name)
    {
        this.userName = name;
    }


    public void addAnnouncement(Announcement announcement) {
        standardWorkerAnnouncements.add(new StandardWorkerAnnouncements(this, announcement.getId()));
    }

    public void updatePoints(Tasks completedTask, int addScore) {
        if(completedTask.isComplete() && (completedTask.getStandardWorker() == this)) {
            this.points += addScore;
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StandardWorker that = (StandardWorker) o;
        return points == that.points &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(hireDate, that.hireDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, hireDate, points);
    }

    @Override
    public String toString() {
        return "StandardWorker{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", hireDate='" + hireDate + '\'' +
                ", points=" + points +
                '}';
    }

    @Override
    public String getRole() {
        return this.getEmployeeRole();
    }
}
