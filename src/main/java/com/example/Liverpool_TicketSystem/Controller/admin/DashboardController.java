package com.example.Liverpool_TicketSystem.Controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.Liverpool_TicketSystem.domain.User;
import com.example.Liverpool_TicketSystem.service.UserService;

@Controller
public class DashboardController {
    private UserService userService;

    public DashboardController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")

    public String getDashboardAdmin(Model model) {
        // Ý tưởng sử dụng vòng lặp bên JSP để in ra danh sách tài khoản

        // Gọi service để lấy danh sách User
        List<User> users = this.userService.layTatCaUser();

        // Tham số thứ nhất "users" là tên biến trong JSP.
        // Tham số thứ hai users là biến trong Java (controller).
        model.addAttribute("users", users);

        return "admin/dashboard/show_accounts";
    }
}
