package com.driver.workFlowManager.model.messaging;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class Message implements Serializable {

    private Integer id;
    private String from;
    private String to;
    private String subject;
    private String messagePayload;
}
