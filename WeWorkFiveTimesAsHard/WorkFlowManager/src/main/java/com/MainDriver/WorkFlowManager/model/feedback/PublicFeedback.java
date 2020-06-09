package com.MainDriver.WorkFlowManager.model.feedback;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublicFeedback {

    @Id
    @GeneratedValue
    private Long id;

    @Type( type = "jsonb" )
    @Column( columnDefinition = "jsonb", name ="all_public_feedback" )
    @Basic(fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Feedback> feedbackList = new ArrayList<Feedback>();

}
