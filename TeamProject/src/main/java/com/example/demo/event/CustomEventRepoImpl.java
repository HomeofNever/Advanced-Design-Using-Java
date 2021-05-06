package com.example.demo.event;

import com.example.demo.user.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * The implementation of the CustomEventRepo interface
 */
public class CustomEventRepoImpl implements CustomEventRepo{
    /**
     * EntityManager attribute
     */
    @Autowired
    private EntityManager entityManager;

    /**
     * @param eventId event id attribute
     * @return the list of the assistant users in that event
     */
    @Override
    public List<User> getAssistants(Long eventId) {
        String queryStr = "" +
                "SELECT user.* FROM user " +
                "JOIN event_user ON event_user.user_id = user.id " +
                "WHERE event_user.event_id = ? AND event_user.role = ?";
        try {
            Query query = entityManager.createNativeQuery(queryStr, User.class);
            query.setParameter(1, eventId);
            query.setParameter(2, EventUser.roles.ASSISTANT.name());

            return (List<User>) query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
