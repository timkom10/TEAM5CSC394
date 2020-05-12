package com.MainDriver.WorkFlowManager.Model.User;

import com.MainDriver.WorkFlowManager.Model.Workers.StandardWorker;
import com.MainDriver.WorkFlowManager.Model.Workers.WorkerTypes;

import javax.persistence.*;
import java.util.Objects;

/*
    Before someone can login, or do some actions, they need to "Become-A" worker type,
    That is, we do not immediately expect someone "To-Be-A" admin or manager type, so
    This class is really the handle for the basic functionalities of any User of the system,
    In addition to the functionalities that would be added on if they acquire a manager, admin,
    Or standard worker type.

 */
@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long UID;

    @OneToOne(cascade = CascadeType.ALL)
    private WorkerTypes userWorkerType;

    private String firstName;
    private String lastName;
    private String hireDate;
    private String role;

    /*Make this User become a standard worker as a default*/
    public Users()
    {
        this.userWorkerType = new StandardWorker(this);
    }

    public WorkerTypes getUserWorkerType() {
        return userWorkerType;
    }

    public void setUserWorkerType(WorkerTypes userWorkerType) {
        this.userWorkerType = userWorkerType;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getHireDate() {
        return hireDate;
    }

    public void setHireDate(String hireDate) {
        this.hireDate = hireDate;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Users{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", hireDate='" + hireDate + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return Objects.equals(userWorkerType, users.userWorkerType) &&
                firstName.equals(users.firstName) &&
                lastName.equals(users.lastName) &&
                hireDate.equals(users.hireDate) &&
                role.equals(users.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, hireDate, role);
    }
}
