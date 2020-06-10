package com.MainDriver.WorkFlowManager.model.projects;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class Task {
    private String taskName;
    private String urgency;
    private String worker;
    private String taskDescription;

    private Long projectId;
    private Integer milestoneId;
    private Integer taskId;

    private int bounty;
    private int upForReview =0;
    private int isComplete =0;
    private int isAssigned =0;

    public Task() {
        taskName = "Available";
        worker = "Available";
    }
}
