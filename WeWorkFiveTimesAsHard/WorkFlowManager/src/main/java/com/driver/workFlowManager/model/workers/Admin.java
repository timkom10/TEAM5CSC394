package com.driver.workFlowManager.model.workers;

import com.driver.workFlowManager.model.messaging.Announcement;
import com.driver.workFlowManager.model.messaging.Message;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "admin", schema = "public")

@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})

@NoArgsConstructor
@AllArgsConstructor
public class Admin extends WorkerType implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String userName;
    private String ROLE;
    private String firstName;
    private String lastName;
    private String hireDate;

    @Type( type = "jsonb" )
    @Column( columnDefinition = "jsonb", name ="messages" )
    @Basic(fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Message> messages = new ArrayList<>();

    @Column(name = "last_message_id")
    private Integer lastMessageKey = 0;

    @Type( type = "jsonb" )
    @Column( columnDefinition = "jsonb", name ="announcements" )
    @Basic(fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Announcement> announcements = new ArrayList<>();

    @Column(name = "last_announcement_id")
    private Integer lastAnnouncementKey = 0;

    public void addMessage(Message message) {
        if(message != null) {
            message.setId(this.lastMessageKey++);
            this.messages.add(message);
        }
    }
    public void addAnnouncement(Announcement announcement) {
        if(announcement != null) {
            announcement.setId(this.lastAnnouncementKey++);
            this.announcements.add(announcement);
        }
    }

    @Override
    public String getRole() {
        return this.ROLE;
    }
}
