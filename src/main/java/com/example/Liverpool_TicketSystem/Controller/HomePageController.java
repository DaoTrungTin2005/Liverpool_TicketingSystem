package com.example.Liverpool_TicketSystem.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageController {
    @GetMapping("/")
    public String getTestPage() {
        return "hello";

    }

    @GetMapping("/signup")
    public String getSignUpPage() {
        return "client/auth/signup";
    }

    @GetMapping("/signin")
    public String getSignInPage() {
        return "client/auth/signin";
    }

}
