package com.example.demo.queue;

import com.example.demo.queue.QueueUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * A class extends the JpaRepository from Spring Framework
 */
@Repository
public interface QueueUserRepo extends JpaRepository<QueueUser, Long> {
    /**
     * @param queueId the the expected QueueUser id
     * @param userId the expected user id
     * @return true if the QueueUser object exists
     */
    boolean existsQueueUserByQueueIdAndUserIdAndEndedAtIsNull(Long queueId, Long userId);

    /**
     * @param userId the expected QueueUser's user id
     * @return the expected QueueUser object
     */
    List<QueueUser> findQueueUsersByUserIdOrderByJoinedAtDesc(Long userId);

    /**
     * @param qid find out the queue user by queue id
     * @return the expected queue user
     */
    QueueUser findQueueUserByIdAndEndedAtIsNull(Long qid);

    /**
     * @param qid find out the queue user in the queue by queue id
     * @return the expected queue user
     */
    QueueUser findQueueUserById(Long qid);
}
