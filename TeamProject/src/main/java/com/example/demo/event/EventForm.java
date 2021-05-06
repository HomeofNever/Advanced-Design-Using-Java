package com.example.demo.event;

import com.example.demo.model.NamedEntity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * EventForm Superclass for handling incoming request
 */
@MappedSuperclass
public class EventForm extends NamedEntity {
    /**
     * Unique token of the event
     */
    @Column(nullable = false)
    private String token;

    /**
     * Default constructor
     */
    public EventForm() {}

    /**
     * Get Event token
     * @return String event unique token
     */
    public String getToken() {
        return token;
    }

    /**
     * Set Event token
     * @param token String event unique token
     */
    public void setToken(String token) {
        this.token = token;
    }
}
