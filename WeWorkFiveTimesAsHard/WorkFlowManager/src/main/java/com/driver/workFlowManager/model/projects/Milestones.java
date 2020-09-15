package com.driver.workFlowManager.model.projects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Milestones {

    private String milestoneName;
    private String description;
    private Integer id;
    private Date dueDate;
    private int isOnSchedule;
    private boolean isComplete;
}
