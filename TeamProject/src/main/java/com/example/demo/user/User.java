package com.example.demo.user;

import com.example.demo.event.EventUser;
import com.example.demo.queue.QueueUser;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Set;

/**
 * User class extends UserForm class
 */
@Entity
public class User extends UserForm {

    /**
     * All the user registered in the event
     */
    @OneToMany(mappedBy="user", fetch = FetchType.EAGER)
    private Set<EventUser> events;

    /**
     * All the QueueUser objects registered in the event
     */
    @OneToMany(mappedBy="user", fetch = FetchType.EAGER)
    private Set<QueueUser> userQueues;

    /**
     * All assistant users in any of the queues in this event
     */
    @OneToMany(mappedBy="user", fetch = FetchType.EAGER)
    private Set<QueueUser> assistantQueues;

    /**
     * Constructor without input argument
     */
    public User() {}

    /**
     * Constructor with input arguments
     * @param username the expected username
     * @param password the expected password
     */
    public User(String username, String password) {
        super(username, password);
    }

    /**
     * @param user construct the User object by another User object
     */
    public User(UserForm user) {
        setStatus(user.getStatus());
        setPassword(user.getPassword());
        setUsername(user.getUsername());
        setDisplayName(user.getDisplayName());
    }
}