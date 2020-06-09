package com.MainDriver.WorkFlowManager.model.projects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    private String taskName;
    private String urgency;
    private String worker;

    private Long projectId;
    private Integer milestoneId;
    private Integer taskId;

    private int bounty;
    private boolean upForReview;
    private boolean isComplete;
    private boolean isAssigned;
}
