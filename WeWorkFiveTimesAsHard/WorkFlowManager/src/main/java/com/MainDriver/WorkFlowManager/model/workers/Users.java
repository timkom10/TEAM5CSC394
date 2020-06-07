package com.MainDriver.WorkFlowManager.model.workers;

import lombok.Data;


import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.*;

@Entity
@Data
@Table(name ="users", schema = "public")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private int active;
    private String roles;
    private String permissions;


    public Users(String username, String password, String roles, String permissions)
    {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.permissions = permissions;
        this.active = 1;
    }

    public Users(){}

    public List<String> getRoleList()
    {
        if(this.roles.length() >0)
        {
            return  Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

    public List<String> getPermissionList()
    {
        if(this.permissions.length() >0) {
            return  Arrays.asList(this.permissions.split(","));
        }
        return new ArrayList<>();
    }
}
