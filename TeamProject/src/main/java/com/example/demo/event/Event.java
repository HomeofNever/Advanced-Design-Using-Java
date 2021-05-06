package com.example.demo.event;

import com.example.demo.log.Log;
import com.example.demo.model.NamedEntity;
import com.example.demo.queue.Queue;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Set;

/**
 * Event Mapping
 */
@Entity
@Table(name = "event")
public class Event extends EventForm {

    /**
     * Users in the event
     */
    @OneToMany(mappedBy="event", fetch = FetchType.EAGER)
    private Set<EventUser> users;

    /**
     * Queues belong to the event
     */
    @OneToMany(mappedBy="event", fetch = FetchType.EAGER)
    private Set<Queue> queues;

    /**
     * Logs related to the event
     */
    @OneToMany(mappedBy="event")
    private Set<Log> logs;

    /**
     * Default Constructor
     */
    public Event() {}

    /**
     * Create mapping object from user request
     * @param e EventForm
     */
    public Event(EventForm e) {
        setToken(e.getToken());
        setName(e.getName());
    }

    /**
     * Get all EventUser objects related to current event
     * @return EventUser
     */
    public Set<EventUser> getUsers() {
        return users;
    }

    /**
     * Get all Queue in the event
     * @return Queues
     */
    public Set<Queue> getQueues() { return queues; }
}
