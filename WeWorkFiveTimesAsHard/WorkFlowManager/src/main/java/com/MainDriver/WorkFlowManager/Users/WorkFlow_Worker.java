package com.MainDriver.WorkFlowManager.Users;

import javax.persistence.*;

/*
    Lists the basic functionalities that we can expect from a Standard Worker
    Think of this class as a class outline rather than implementation a .h (header) c/c++ file
    If you are familiar with those
 */

@Entity
public class WorkFlow_Worker
{

    /*AUTO- Generate the ID for us*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*Not auto generated*/
    private String firstName;
    private String lastName;
    private String department;
    private String role;

    public String getDepartment() { return department; }

    public void setDepartment(String department) { this.department = department; }

    public String getRole() { return role; }

    public void setRole(String role) { this.role = role; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }
}
