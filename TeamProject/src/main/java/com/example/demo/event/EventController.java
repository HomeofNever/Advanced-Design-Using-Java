package com.example.demo.event;

import com.example.demo.queue.Queue;
import com.example.demo.queue.QueueRepo;
import com.example.demo.queue.QueueUserRepo;
import com.example.demo.user.User;
import com.example.demo.user.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Random;

/**
 * Controller class of Event/EventUser related behavior
 */
@RestController
@RequestMapping("/event")
public class EventController {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private EventRepo eventRepo;

    @Autowired
    private EventUserRepo eventUserRepo;

    @Autowired
    private QueueRepo queueRepo;

    @Autowired
    private QueueUserRepo queueUserRepo;

    /**
     * Create an event and link ADMIN to current user
     * @param user current login user
     * @param event event to create
     * @return event created
     */
    @PostMapping("/create")
    @Transactional
    public Event createEvent(@AuthenticationPrincipal UserDetails user, @RequestBody EventForm event) {
        if (event.getToken() == null) {
            event.setToken(randomToken());
        }

        User u = userRepo.findByUsername(user.getUsername());

        Event e = new Event(event);
        e = eventRepo.save(e);

        // Link User with Event
        EventUser eu = new EventUser();
        eu.setEvent(e);
        eu.setUser(u);
        eu.setRole(EventUser.roles.ADMIN);
        eventUserRepo.save(eu);

        return e;
    }

    /**
     * update an existing under an user
     * @param user authenticated user
     * @param event existing changed event
     * @param eventId origin event id
     * @return changed event
     */
    @PostMapping("/{eventId}/update")
    @Transactional
    public Event updateEvent(@AuthenticationPrincipal UserDetails user, @RequestBody EventForm event, @PathVariable Long eventId) {
        Event e = eventRepo.findEventById(eventId);
        if (e == null) {
            throw new RuntimeException("Unable to find given event with current User!");
        }

        User u = userRepo.findByUsername(user.getUsername());
        EventUser eu = eventUserRepo.findEventUserByEventIdAndUserId(eventId, u.getId());

        if (!EventUser.isAdmin(eu.getRole())) {
            throw new RuntimeException("Unable to alter event without ADMIN permission!");
        }

        if (event.getToken() != null) {
            e.setToken(event.getToken());
        }

        if (event.getName() != null) {
            e.setName(event.getName());
        }

        return eventRepo.save(e);
    }

    /**
     * Get event info
     * @param user Authenticated user
     * @param eventId id of event to fetch
     * @return event requested
     */
    @GetMapping("/{eventId}")
    public Event getEventInfo(@AuthenticationPrincipal UserDetails user, @PathVariable Long eventId) {
        User u = userRepo.findByUsername(user.getUsername());
        EventUser eu = checkUserEventRelationship(eventId, u.getId());
        Event e = eventRepo.findEventById(eventId);
        if (EventUser.canManage(eu.getRole())) {
            e.getUsers(); // Eager load?
        }

        return e;
    }

    /**
     * User leaving event
     * @param user authenticated user
     * @param eventId id of event to leave
     */
    @DeleteMapping("/{eventId}")
    public void leaveEvent(@AuthenticationPrincipal UserDetails user, @PathVariable Long eventId) {
        User u = userRepo.findByUsername(user.getUsername());
        eventUserRepo.deleteEventUserByEventIdAndUserId(u.getId(), eventId);
    }

    /**
     * Join an event
     * @param user Authenticated user
     * @param eventToken unique token of the event
     * @return joined event
     */
    @PostMapping("/{eventToken}/join")
    public Event joinEvent(@AuthenticationPrincipal UserDetails user, @PathVariable String eventToken) {
        Event e = eventRepo.findEventByToken(eventToken);
        if (e == null) {
            throw new EventNotFoundException("Unable to find event with given token");
        }

        User u = userRepo.findByUsername(user.getUsername());
        EventUser eu = new EventUser();
        eu.setUser(u);
        eu.setEvent(e);
        eu.setRole(EventUser.roles.USER);
        eventUserRepo.save(eu);

        return e;
    }

    /**
     * get assistants of the event
     * @param user Authenticated user
     * @param eventId id of event
     * @return list of User role of assistant in the event
     */
    @GetMapping("/{eventId}/assistants")
    public List<User> getAssistants(@AuthenticationPrincipal UserDetails user, @PathVariable Long eventId) {
        User u = userRepo.findByUsername(user.getUsername());
        checkUserEventRelationship(eventId, u.getId());

        return eventRepo.getAssistants(eventId);
    }

    /**
     * promote a user to assistant in an event
     * @param user Authenticated user
     * @param eventId event the user will become assistant
     * @param username the username of the user
     * @return List of assistant in the event
     */
    @PostMapping("/{eventId}/assistant/{username}")
    public List<User> addAssistant(@AuthenticationPrincipal UserDetails user, @PathVariable Long eventId, @PathVariable String username) {
        User u = userRepo.findByUsername(user.getUsername());
        EventUser eu = checkUserEventRelationship(eventId, u.getId());

        if (!EventUser.isAdmin(eu.getRole())) {
            throw new RuntimeException("Unable to alter event without ADMIN permission!");
        }

        u = userRepo.findByUsername(username);
        if (u == null) {
            throw new RuntimeException("Unable to find target user with username!");
        }

        eu = eventUserRepo.findEventUserByEventIdAndUserId(eu.getEvent().getId(), u.getId());
        if (eu == null) {
            throw new RuntimeException("User specify is not in the event!");
        }
        eu.setRole(EventUser.roles.ASSISTANT);
        eventUserRepo.save(eu);

        return eventRepo.getAssistants(eventId);
    }

    /**
     * Add queue to the event
     * @param user Authenticated user
     * @param eventId id of the event
     * @param queue queue to be added to event
     * @return The queue created
     */
    @PostMapping("/{eventId}/queue/add")
    public Queue addQueue(@AuthenticationPrincipal UserDetails user, @PathVariable Long eventId, @RequestBody Queue queue) {
        User u = userRepo.findByUsername(user.getUsername());
        EventUser eu = checkUserEventRelationship(eventId, u.getId());

        if (!EventUser.isAdmin(eu.getRole())) {
            throw new RuntimeException("Unable to alter event without ADMIN permission!");
        }

        // test Queue
        if (queue.getName() == null) {
            throw new RuntimeException("Queue name must be set!");
        }

        queue.setEvent(eu.getEvent());

        return queueRepo.save(queue);
    }


    /**
     * Get queues of the event
     * @param user Authenticated user
     * @param eventId id of the event
     * @return List of queues in the event
     */
    @GetMapping("/{eventId}/queues/")
    public List<Queue> eventQueues(@AuthenticationPrincipal UserDetails user, @PathVariable Long eventId) {
        User u = userRepo.findByUsername(user.getUsername());
        checkUserEventRelationship(eventId, u.getId());

        return queueRepo.findQueuesByEventId(eventId);
    }

    /**
     * downgrade assistant to user in the event
     * @param user Authenticated user
     * @param eventId id of the event
     * @param username the username of user to be downgraded
     * @return List of assistants currently in the event
     */
    @DeleteMapping("/{eventId}/assistant/{username}")
    public List<User> deleteAssistant(@AuthenticationPrincipal UserDetails user, @PathVariable Long eventId, @PathVariable String username) {
        User u = userRepo.findByUsername(user.getUsername());
        EventUser eu = checkUserEventRelationship(eventId, u.getId());

        if (!EventUser.isAdmin(eu.getRole())) {
            throw new RuntimeException("Unable to alter event without ADMIN permission!");
        }

        eventUserRepo.deleteEventUserByEventIdAndRoleAndUsername(eventId, EventUser.roles.ASSISTANT.name(), username);

        return eventRepo.getAssistants(eventId);
    }

    private String randomToken() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 6;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }

    private EventUser checkUserEventRelationship(Long eventId, Long userId) {
        EventUser e = eventUserRepo.findEventUserByEventIdAndUserId(eventId, userId);
        if (e == null) {
            throw new RuntimeException("Unable to locate user in event!");
        }

        return e;
    }
}

/**
 * Exception thrown when no related event found
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
class EventNotFoundException extends RuntimeException {
    public EventNotFoundException(String message) {
        super(message);
    }
}