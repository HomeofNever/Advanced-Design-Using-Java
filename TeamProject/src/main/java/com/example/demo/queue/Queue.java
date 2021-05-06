package com.example.demo.queue;

import com.example.demo.event.Event;
import com.example.demo.model.NamedEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Set;

/**
 * The class Extends
 */
@Entity
@Table(name = "queue")
public class Queue extends NamedEntity {
    /**
     * The queue status
     */
    @Column(columnDefinition = "varchar(10) default 'open'")
    private String status;

    /**
     * The event which current queue is belonging to
     */
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name="event_id", nullable=false)
    private Event event;

    /**
     * All users who joined the queue
     */
    @OneToMany(mappedBy="queue")
    private Set<QueueUser> users;

    /**
     * @return the current queue status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @return the event which current queue is belonging to
     */
    public Event getEvent() {
        return event;
    }

    /**
     * @return all users who joined the queue
     */
    public Set<QueueUser> getUsers() {
        return users;
    }

    /**
     * @param event the expected event which current queue to set to
     */
    public void setEvent(Event event) {
        this.event = event;
    }

    /**
     * @param status the expected status to set to
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
