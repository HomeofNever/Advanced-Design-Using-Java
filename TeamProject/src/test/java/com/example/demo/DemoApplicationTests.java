package com.example.demo;

import com.example.demo.event.Event;
import com.example.demo.event.EventUser;
import com.example.demo.user.User;
import com.example.demo.event.EventRepo;
import com.example.demo.event.EventUserRepo;
import com.example.demo.user.UserRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private EventRepo eventRepo;

    @Autowired
    private EventUserRepo eventUserRepo;

    @Test
    void contextLoads() {
        User u1 = new User("bob", "1111");
        User u2 = new User("caven", "222");

        userRepo.save(u1);
        userRepo.save(u2);

        Event e1 = new Event();
        e1.setName("e1");
        e1.setToken("some token");

        eventRepo.save(e1);

        EventUser eu1 = new EventUser();
        eu1.setUser(u1);
        eu1.setEvent(e1);
        eu1.setRole("ADMIN");
        EventUser eu2 = new EventUser();
        eu2.setUser(u2);
        eu2.setEvent(e1);
        eu2.setRole("USER");

        eventUserRepo.save(eu1);
        eventUserRepo.save(eu2);

        User u3 = userRepo.findByUsername("bob"); // Avoid N + 1 Problem
        System.out.println(u3.getUsername());

        for (EventUser u : eventUserRepo.getEventUserByEvent(e1)) {
            System.out.println(u.getUser().getUsername() + " " +u.getRole());
        }
    }

}
