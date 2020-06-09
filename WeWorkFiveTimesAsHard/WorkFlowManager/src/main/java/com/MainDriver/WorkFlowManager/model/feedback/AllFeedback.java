package com.MainDriver.WorkFlowManager.model.feedback;
import lombok.Data;
import javax.persistence.*;

@Entity
@Data
public class AllFeedback {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name ="feedback_subject")
    private String subject;

    @Column(name ="content_payload")
    @Lob
    private String content;

    @Column(name = "to_user")
    private String to;

    @Column(name = "from_user")
    private String from;

    public AllFeedback() {
    }

}
