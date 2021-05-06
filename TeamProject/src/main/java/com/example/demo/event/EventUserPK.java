package com.example.demo.event;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * EventUser Primary Key
 */
@Embeddable
public class EventUserPK implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * user's id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * event's id
     */
    @Column(name = "event_id")
    private Long eventId;

    /**
     * Get user id of the user
     * @return Long id of user
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Get event id of the event
     * @return Long id of event
     */
    public Long getEventId() {
        return eventId;
    }

    /**
     * Set user id
     * @param userId user's id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Set Event id
     * @param eventId event's id
     */
    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventUserPK)) return false;
        EventUserPK that = (EventUserPK) o;
        return userId.equals(that.userId) && eventId.equals(that.eventId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, eventId);
    }
}
