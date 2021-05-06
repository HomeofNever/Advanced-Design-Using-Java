package com.example.demo.event;

import com.example.demo.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The EventRepo which extends the JpaRepository
 */
@Repository
public interface EventRepo extends JpaRepository<Event, Long>, CustomEventRepo {
    /**
     * @param id expected id
     * @return the expected Event
     */
    Event findEventById(Long id);

    /**
     * @param token expected token
     * @return the expected Event
     */
    Event findEventByToken(String token);
}
