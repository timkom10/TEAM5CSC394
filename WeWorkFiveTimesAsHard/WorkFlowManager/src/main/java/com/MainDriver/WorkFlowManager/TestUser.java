package com.MainDriver.WorkFlowManager;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class TestUser {


    @Id
    @GeneratedValue
    private Long id;

    //Not auto generated
    private String firstname;

   public  TestUser()
    {
        firstname =" ";
    }

   public TestUser(String first)
    {
        //Id is auto generated
        this.firstname  = first;
    }


    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
}
