package com.example.demo.event;

import com.example.demo.user.User;

import java.util.List;

/**
 * Interface for customizing EventRepo
 */
public interface CustomEventRepo {
    /**
     * @param eventId event id attribute
     * @return the list of the assistant users in that event
     */
    List<User> getAssistants(Long eventId);
}
