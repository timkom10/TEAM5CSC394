package com.MainDriver.WorkFlowManager.model.workers;

import com.MainDriver.WorkFlowManager.model.announcements.Announcement;
import com.MainDriver.WorkFlowManager.model.feedback.Feedback;
import com.MainDriver.WorkFlowManager.model.projects.Project;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@Table(name= "manager", schema = "public")
public class Manager extends WorkerType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(cascade = CascadeType.PERSIST,
            mappedBy = "manager", orphanRemoval = true)
    private Set<StandardWorker> dominion = new HashSet<StandardWorker>();

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "manager")
    private Set<Project> projects = new HashSet<Project>();

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "manager")
    private Set<Announcement> announcements = new HashSet<Announcement>();

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "manager")
    private Set<Feedback> feedbacks = new HashSet<Feedback>();

    private String userName;
    private String firstName;
    private String lastName;
    private String hireDate;
    private String ROLE;

    public Manager() {
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Manager manager = (Manager) o;
        return Objects.equals(firstName, manager.firstName) &&
                Objects.equals(lastName, manager.lastName) &&
                Objects.equals(hireDate, manager.hireDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, hireDate);
    }

    @Override
    public String toString() {
        return "Manager{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", hireDate='" + hireDate + '\'' +
                '}';
    }

    @Override
    public String getRole() {
        return this.ROLE;
    }
}
