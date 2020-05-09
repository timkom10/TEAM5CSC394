package com.MainDriver.WorkFlowManager.Model.Feedback;

import com.MainDriver.WorkFlowManager.Model.User.Users;

import javax.persistence.*;

@Entity
public abstract class FeedbackSystem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected  int ID;

    @OneToOne
    protected Users user;



}
