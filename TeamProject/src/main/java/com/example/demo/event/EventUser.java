package com.example.demo.event;

import com.example.demo.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;

/**
 * EventUser relationship mapping
 */
@Entity
@Table(name = "event_user")
public class EventUser implements Serializable {

    /**
     * User's role in the event
     */
    public enum roles {ADMIN, ASSISTANT, USER};

    // https://stackoverflow.com/questions/61610701/jpa-2-0-many-to-many-with-extra-column-update-collection
    // https://stackoverflow.com/questions/23837561/jpa-2-0-many-to-many-with-extra-column
    /**
     * Primary key of EventUser relationship
     */
    @EmbeddedId
    private EventUserPK id = new EventUserPK();  // Hibernate need this

    /**
     * User in the event
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;

    /**
     * User joined event
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("eventId")
    private Event event;

    /**
     * Role the user in the event
     */
    @Column(nullable = false)
    private String role;

    /**
     * get user
     * @return User
     */
    public User getUser() {
        return user;
    }

    /**
     * Get event
     * @return event
     */
    public Event getEvent() {
        return event;
    }

    /**
     * Get role
     * @return Role
     */
    public String getRole() {
        return role;
    }

    /**
     * set user
     * @param user User
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * set event
     * @param event event
     */
    public void setEvent(Event event) {
        this.event = event;
    }

    /**
     * set role
     * @param role String representation of roles
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * set role
     * @param r EventUser.role
     */
    public void setRole(roles r) {
        this.role = r.name();
    }

    /**
     * Test if role is admin
     * @param role String representation of roles
     * @return true if it is admin, false otherwise
     */
    public static boolean isAdmin(String role) {
        return roles.ADMIN.name().equals(role);
    }

    /**
     * Test if role is assistant
     * @param role String representation of roles
     * @return true if it is assistant, false otherwise
     */
    public static boolean isAssistant(String role) {
        return roles.ASSISTANT.name().equals(role);
    }

    /**
     * Test if role is user
     * @param role String representation of roles
     * @return true if it is user, false otherwise
     */
    public static boolean isUser(String role) { return roles.USER.name().equals(role); }

    /**
     * Test if role is can manage event
     * @param role String representation of roles
     * @return true is it is admin or assistant, false otherwise
     */
    public static boolean canManage(String role) { return isAdmin(role) || isAssistant(role); }
}
