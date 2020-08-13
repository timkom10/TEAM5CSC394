package com.MainDriver.WorkFlowManager.model.feedback;
import lombok.Data;
import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class AllFeedback {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name ="feedback_subject")
    private String subject;

    @Column(name ="content_payload") @Lob
    private String content;

    @Column(name = "to_user")
    private String to;

    @Column(name = "from_user")
    private String from;

    @Column(name = "date_written")
    private Date date;

    public AllFeedback() {
    }

}
