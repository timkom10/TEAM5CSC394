package com.driver.workFlowManager.model.projects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Milestones {
    private String milestoneName;
    private String description;
    private String dueDate;
    private Integer id;
    private int isOnSchedule;
    private boolean isComplete;
}
