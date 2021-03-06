package com.driver.workFlowManager.model.feedback;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {
    private Integer id;
    private String subject;
    private String content;
    private String to;
    private String from;
}
