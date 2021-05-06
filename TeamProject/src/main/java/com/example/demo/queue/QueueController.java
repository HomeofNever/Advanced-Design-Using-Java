package com.example.demo.queue;

import com.example.demo.event.Event;
import com.example.demo.event.EventUser;
import com.example.demo.event.EventUserRepo;
import com.example.demo.user.User;
import com.example.demo.user.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * The class handling the operation of joining users into the queue, and fetching the expected queue info
 */
@RestController
@RequestMapping("/queue")
public class QueueController {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private QueueRepo queueRepo;

    @Autowired
    private QueueUserRepo queueUserRepo;

    @Autowired
    private EventUserRepo eventUserRepo;

    /**
     * @param user the expected user
     * @param queueId the expected queue id
     * @return the expected queue if found, or return null
     */
    @GetMapping("/{queueId}")
    public Set<QueueUser> showQueue(@AuthenticationPrincipal UserDetails user, @PathVariable Long queueId) {
        // Only canManage should be able to list all queueUser in the Queue
        User u = userRepo.findByUsername(user.getUsername());
        Queue q = queueRepo.findQueueById(queueId);
        if (q == null) {
            throw new RuntimeException("Cannot find given queue!");
        }
        EventUser eu = eventUserRepo.findEventUserByEventIdAndUserId(q.getEvent().getId(), u.getId());
        if (EventUser.isAdmin(eu.getRole()) || EventUser.isAssistant(eu.getRole())) {
            return q.getUsers();
        }
        return null;
    }

    /**
     * @param user the expected user
     * @param queueId the expected queue id
     * @param queueUser the expected queue user
     * @return the expected user instance in the QueueUserRepo object
     */
    @PostMapping("/{queueId}/join")
    public QueueUser joinQueue(@AuthenticationPrincipal UserDetails user, @PathVariable Long queueId, @RequestBody QueueUser queueUser) {
        Queue queue = queueRepo.findQueueById(queueId);
        Event e = queue.getEvent();
        if (queue == null) {
            throw new RuntimeException("Unable to locate queue!");
        }

        User u = userRepo.findByUsername(user.getUsername());
        if (!eventUserRepo.existsEventUserByEventIdAndUserId(e.getId(), u.getId())) {
            throw new RuntimeException("Unable to locate user in event!");
        }

        // First, check if user is currently in the queue already, with status waiting
        if (queueUserRepo.existsQueueUserByQueueIdAndUserIdAndEndedAtIsNull(queueId, u.getId())) {
            throw new RuntimeException("User is already waiting/in progress in the queue!");
        }

        QueueUser qu = new QueueUser();
        qu.setUser(u);
        qu.setQueue(queue);
        // Allow user enter notes, only, for now.
        qu.setNote(queueUser.getNote());
        qu.setStatus(QueueUser.QUEUE_USER_STATUS.WAITING);

        return queueUserRepo.save(qu);
    }
}
