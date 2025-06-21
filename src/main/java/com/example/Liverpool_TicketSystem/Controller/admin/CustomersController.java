package com.example.Liverpool_TicketSystem.Controller.admin;

import javax.swing.Spring;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Liverpool_TicketSystem.domain.User;
import com.example.Liverpool_TicketSystem.service.UserService;

@Controller
public class CustomersController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public CustomersController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/admin/create")
    public String getCreateCustomersPage(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/customers/create";
    }

    // @ModelAttribute("newUser") User user: Spring sẽ lấy dữ liệu từ form HTTP POST
    // và ánh xạ nó vào một đối tượng User mới

    @PostMapping("/admin/create")
    public String getCreateCustomersPage(@ModelAttribute("newUser") User user) {
        String hashPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);

        // 1. Dữ liệu được gửi lên từ form

        // Với path="role.name", Spring sẽ tự động:

        // Tạo một User object mới (hoặc đã có sẵn)
        // Trong User, sẽ có một thuộc tính Role role
        // Spring sẽ tự tạo đối tượng Role, rồi gán role.setName("ADMIN") hoặc "USER"
        // tùy vào lựa chọn của người dùng

        // 👉 Kết quả là:
        // Khi @ModelAttribute("newUser") User user được nhận vào controller, thì:
        // user.getRole() không null
        // user.getRole().getName() = "ADMIN" (ví dụ)

        // Tuy nhiên, chỉ có mỗi cái tên ("ADMIN"), chứ không có ID hoặc các thông tin
        // khác của Role trong database.

        // ===========

        // 2. Tại sao phải gọi layRoleTheoTen()?
        // Dòng code:
        // user.setRole(this.userService.layRoleTheoTen(user.getRole().getName()));

        // có tác dụng:
        // ✅ Dùng user.getRole().getName() lấy ra tên từ form (ví dụ: "ADMIN")
        // ✅ Gọi tới userService.layRoleTheoTen(...) (đây là một hàm sẽ truy vấn CSDL để
        // lấy full object Role có name = "ADMIN")
        // ✅ Sau đó, bạn dùng user.setRole(...) để gán lại Role này cho User

        // ===========

        // 🔁 Tóm tắt:
        // role.name trong form (JSP)
        // → Chỉ giúp Spring lấy cái tên của role mà người dùng chọn (ví dụ: "ADMIN"
        // hoặc "USER")

        // Gọi layRoleTheoTen(...) trong controller
        // → Dùng cái name đó để tìm ra đối tượng Role đầy đủ trong CSDL, bao gồm:
        // id
        // name
        // Các thông tin khác (nếu có)

        // Gán lại vào user.setRole(...)
        // → Đảm bảo rằng user.role là một Role đã có sẵn trong database và Hibernate
        // hiểu đúng
        user.setRole(this.userService.layRoleTheoTen(user.getRole().getName()));

        this.userService.luuThongTinUser(user);
        return "redirect:/admin/accounts";

    }
}
