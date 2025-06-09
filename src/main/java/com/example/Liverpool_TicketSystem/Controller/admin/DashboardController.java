package com.example.Liverpool_TicketSystem.Controller.admin;

import org.springframework.web.bind.annotation.GetMapping;

public class DashboardController {
    @GetMapping("/admin")
    public String getDashboard() {
        return "admin/dashboard/show";
    }
}
