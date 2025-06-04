package com.example.Liverpool_TicketSystem.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
    @GetMapping("/")
    public String getTestPage() {
        return "hello";

    }
}
