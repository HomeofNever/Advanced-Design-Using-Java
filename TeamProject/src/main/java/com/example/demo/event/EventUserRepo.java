package com.example.demo.event;

import com.example.demo.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Event User Repository
 */
@Repository
public interface EventUserRepo extends JpaRepository<EventUser, Long> {
    /**
     * Get EventUser by event
     * @param e Event
     * @return List of EventUser
     */
    List<EventUser> getEventUserByEvent(Event e);

    /**
     * find EventUser by user
     * @param u User
     * @return List of EventUser
     */
    List<EventUser> findEventUsersByUser(User u);

    /**
     * find EventUser by eventid and userid
     * @param eventId event id
     * @param userId user id
     * @return EventUser
     */
    EventUser findEventUserByEventIdAndUserId(Long eventId, Long userId);

    /**
     * delete event user by event id and user id
     * @param eventId event id
     * @param userId user id
     */
    void deleteEventUserByEventIdAndUserId(Long eventId, Long userId);

    /**
     * Test if given event id and user id owned by one EventUser object
     * @param eventId event's id
     * @param userId user's id
     * @return true if exists any, false otherwise
     */
    boolean existsEventUserByEventIdAndUserId(Long eventId, Long userId);

    /**
     * Remove an user from event based on event id and username and role
     * @param eventId event id
     * @param role EventUser.role
     * @param username username of user
     */
    @Modifying
    @Query("DELETE FROM EventUser WHERE id.eventId = ?1 AND role = ?2 AND id.userId IN (SELECT id FROM User WHERE username = ?3)")
    void deleteEventUserByEventIdAndRoleAndUsername(Long eventId, String role, String username);
}
