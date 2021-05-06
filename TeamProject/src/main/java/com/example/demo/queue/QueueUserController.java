package com.example.demo.queue;

import com.example.demo.event.EventUser;
import com.example.demo.event.EventUserRepo;
import com.example.demo.user.User;
import com.example.demo.user.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

/**
 * The class handle the operation of the QueueUser object
 */
@RestController
@RequestMapping("/line")
public class QueueUserController {

    /**
     * the UserRepo attribute
     */
    @Autowired
    UserRepo userRepo;

    /**
     * the QueueUserRepo attribute
     */
    @Autowired
    QueueUserRepo queueUserRepo;

    /**
     * the EventUserRepo attribute
     */
    @Autowired
    EventUserRepo eventUserRepo;

    /**
     * @param user the expected user
     * @param queueUserId the expected id of the QueueUser object
     * @return the queue if found, or return null
     */
    @GetMapping("/{queueUserId}")
    public QueueUser getQueueUser(@AuthenticationPrincipal UserDetails user, @PathVariable Long queueUserId) {
        QueueUser qu = queueUserRepo.findQueueUserById(queueUserId);
        User u = userRepo.findByUsername(user.getUsername());
        if (qu.getUser().getId().equals(u.getId())) {
            return qu;
        } else if (qu.getAssistant() != null) {
            if (qu.getAssistant().getId().equals(u.getId())) {
                return qu;
            }
        }
        return null;
    }

    /**
     * @param user the expected user
     * @param queueUserId the expected id of the QueueUser object
     * @return the QueueUser who is assisting other users
     */
    @PostMapping("/{queueUserId}/assist")
    public QueueUser assistQueueUser(@AuthenticationPrincipal UserDetails user, @PathVariable Long queueUserId) {
        User u = userRepo.findByUsername(user.getUsername());
        QueueUser qu = queueUserRepo.findQueueUserById(queueUserId);
        if (qu == null) {
            throw new RuntimeException("Unable to find given queue user!");
        }

        if (qu.getEndedAt() != null) {
            throw new RuntimeException("Unable to assist an ended queue request!");
        }

        Queue q = qu.getQueue();

        EventUser eu = eventUserRepo.findEventUserByEventIdAndUserId(u.getId(), q.getEvent().getId());
        if (eu == null || !EventUser.canManage(eu.getRole())) {
            throw new RuntimeException("Unable to assistant with current role/not in event");
        }

        qu.setAssistant(u);
        qu.setStatus(QueueUser.QUEUE_USER_STATUS.IN_PROGRESS);

        return queueUserRepo.save(qu);
    }

    /**
     * @param user the expected user
     * @param queueUserId the expected QueueUser id
     * @return the queue user who left the queue
     */
    @PostMapping("/{queueUserId}/end")
    public QueueUser endQueueUser(@AuthenticationPrincipal UserDetails user, @PathVariable Long queueUserId) {
        User u = userRepo.findByUsername(user.getUsername());

        // First, check if it is a user or an assistant trying to end queue
        QueueUser qu = queueUserRepo.findQueueUserByIdAndEndedAtIsNull(queueUserId);

        if (qu == null) {
            throw new RuntimeException("Unable to locate active queue User!");
        }

        qu.setEndedAt(new Timestamp(System.currentTimeMillis()));

        if (qu.getUser().getId().equals(u.getId())) {
            // Ended by user
            qu.setStatus(QueueUser.QUEUE_USER_STATUS.LEFT);
        } else if (qu.getAssistant().getId().equals(u.getId())) {
            qu.setStatus(QueueUser.QUEUE_USER_STATUS.FINISHED);
        } else {
            // @TODO allow admin to manage queue user
            throw new RuntimeException("No Permission to edit this Queue User");
        }

        return queueUserRepo.save(qu);
    }
}

