package com.example.demo.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Class sending welcome text
 */
@Controller
public class WelcomeController {

    /**
     * @return welcome text
     */
    @GetMapping("/")
    public String welcome() {
        return "welcome";
    }
}
