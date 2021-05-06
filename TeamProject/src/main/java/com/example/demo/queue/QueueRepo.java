package com.example.demo.queue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The class extends the JpaRepository from Spring Framework
 */
@Repository
public interface QueueRepo extends JpaRepository<Queue, Long> {
    /**
     * Find queue by id
     * @param id Queue's id
     * @return Queue
     */
    Queue findQueueById(Long id);

    /**
     * find queue by event id and event
     * @param eventID event's id
     * @param id queue's id
     * @return Queue
     */
    Queue findQueueByEventIdAndId(Long eventID, Long id);

    /**
     * Find queues by event id
     * @param eventId event's id
     * @return List of queues
     */
    List<Queue> findQueuesByEventId(Long eventId);
}