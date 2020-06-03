package com.MainDriver.WorkFlowManager.model.workers;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "admin", schema = "public")
public class Admin extends  WorkerType{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private String userName;
    private String ROLE;
    private String firstName;
    private String lastName;
    private String hireDate;

    @Override
    public String getRole() {
        return this.ROLE;
    }
}
