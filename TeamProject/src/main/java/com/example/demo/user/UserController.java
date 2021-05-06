package com.example.demo.user;

import com.example.demo.WebSecurityConfig;
import com.example.demo.event.EventUser;
import com.example.demo.event.EventUserRepo;
import com.example.demo.queue.QueueUser;
import com.example.demo.queue.QueueUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * UserController Class:
 * @version 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {
    /**
     * The UserRepo attribute
     */
    @Autowired
    private UserRepo userRepo;

    /**
     * The EventUserRepo attribute
     */
    @Autowired
    private EventUserRepo eventUserRepo;

    /**
     * The QueueUserRepo attribute
     */
    @Autowired
    private QueueUserRepo queueUserRepo;

    /**
     * @param user the user to be set as the current suer
     * @return the user found in the userRepo
     */
    @GetMapping("/me")
    public User getUser(@AuthenticationPrincipal UserDetails user) {
        return userRepo.findByUsername(user.getUsername());
    }

    /**
     * @param user the user to be registered
     * @return the registered user
     */
    @PostMapping("/register")
    public User processRegisterForm(@RequestBody UserForm user) {
        checkPassword(user.getPassword());
        user.setPassword(encodePassword(user.getPassword()));
        if (userRepo.findByUsername(user.getUsername()) != null) {
            throw new RuntimeException("Username " + user.getUsername() + " has been used. Please try another one");
        }
        User u = new User(user);

        return userRepo.save(u);
    }

    /**
     * @param user the target user, the info of whom to be update
     * @param payload payload map
     * @return the user which has been saved into the userRepo
     */
    @PostMapping("/update")
    public User updateUser(@AuthenticationPrincipal UserDetails user, @RequestBody Map<String, String> payload) {
        // Only allow password, username
        String password = payload.get("password");
        String displayName = payload.get("displayName");
        String status = payload.get("status");

        User u = userRepo.findByUsername(user.getUsername());
        if (password != null) {
            checkPassword(password);
            u.setPassword(encodePassword(password));
        }

        if (displayName != null) {
            u.setDisplayName(displayName);
        }

        if (status != null) {
            u.setStatus(status);
        }

        return userRepo.save(u);
    }

    /**
     * @param user user to be authenticated and for event searching
     * @return the queue which the user has registered with
     */
    @GetMapping("/events")
    public List<EventUser> userEvent(@AuthenticationPrincipal UserDetails user) {
        return eventUserRepo.findEventUsersByUser(userRepo.findByUsername(user.getUsername()));
    }

    /**
     * @param user user to be authenticated and for queue searching
     * @return the queue which the user has joined in
     */
    @GetMapping("/queues")
    public List<QueueUser> userQueue(@AuthenticationPrincipal UserDetails user) {
        User u = userRepo.findByUsername(user.getUsername());
        // @TODO Optimize here
        List<QueueUser> qu = queueUserRepo.findQueueUsersByUserIdOrderByJoinedAtDesc(u.getId());
        return qu;
    }

    /**
     * Encoding should align with WebSecurity
     * @param password
     * @return hashed password
     */
    private String encodePassword(String password) {
        return WebSecurityConfig.passwordEncoder().encode(password);
    }

	/**
	 * checkPassword checks that the password at least 10 characters long
	 * @param password - user password string String
	 * @throws RuntimeException if the password is less than 10 characters long.
	 */
    private void checkPassword(String password) {
        if (password.length() < 10) {
            throw new RuntimeException("Password should at least be 10 character long!");
        }
    }
}
