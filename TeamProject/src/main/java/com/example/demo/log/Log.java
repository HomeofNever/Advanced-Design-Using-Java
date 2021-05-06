package com.example.demo.log;

import com.example.demo.event.Event;
import com.example.demo.model.BaseEntity;
import com.example.demo.user.User;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * The class for log which extends the BaseEntity
 */
@Entity
@Table(name = "log")
public class Log extends BaseEntity {
    /**
     * The event id which the log is belonging to
     */
    @ManyToOne
    @JoinColumn(name="event_id", nullable=false)
    private Event event;

    /**
     * The user id which the log is belonging to
     */
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    /**
     * The type of the log
     */
    @Column(nullable = false)
    private String type;

    /**
     * The description attribute
     */
    private String description;

    /**
     * The timestamp attribute
     */
    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp timestamp;

    /**
     * @return the current event
     */
    public Event getEvent() {
        return event;
    }

    /**
     * @return the current description stored in the object
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the current type
     */
    public String getType() {
        return type;
    }

    /**
     * @return the current timestamp
     */
    public Timestamp getTimestamp() {
        return timestamp;
    }

    /**
     * @param user the expected user to set to
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @param event the expected event to set to
     */
    public void setEvent(Event event) {
        this.event = event;
    }

    /**
     * @param type the type event to set to
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @param description the expected description to set to
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
