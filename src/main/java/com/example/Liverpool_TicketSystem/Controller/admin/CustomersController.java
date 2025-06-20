package com.example.Liverpool_TicketSystem.Controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class CustomersController {
    @GetMapping("/admin/create")
    public String getDashboard() {
        return "admin/customers/create";
    }
}
