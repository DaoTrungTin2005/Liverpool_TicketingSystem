package com.example.Liverpool_TicketSystem.Controller.admin;

import java.util.List;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.Liverpool_TicketSystem.domain.Role;
import com.example.Liverpool_TicketSystem.domain.User;
import com.example.Liverpool_TicketSystem.domain.dto.CreateAccountDTO;
import com.example.Liverpool_TicketSystem.service.UserService;

import jakarta.validation.Valid;

@Controller
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    // Ý tưởng về việc sử dụng DTO để validate tương tự với ở client/HomePageController

    @GetMapping("/admin/accounts/create")
    public String getCreateAccountsPage(Model model) {
        // model.addAttribute("newUser", new User());

        model.addAttribute("newUser", new CreateAccountDTO());
        return "admin/accounts/create";
    }

    // @ModelAttribute("newUser") User user: Spring sẽ lấy dữ liệu từ form HTTP POST
    // và ánh xạ nó vào một đối tượng User mới

    @PostMapping("/admin/accounts/create")
    public String getCreateAccountsPage(@ModelAttribute("newUser") @Valid CreateAccountDTO createAccountDTO,
            BindingResult newUserBindingResult) {

        List<FieldError> errors = newUserBindingResult.getFieldErrors();
        if (newUserBindingResult.hasErrors()) {
            return "admin/accounts/create";
        }
        User user = new User();
        user.setUsername(createAccountDTO.getUsername());
        user.setEmail(createAccountDTO.getEmail());

        String hashPassword = this.passwordEncoder.encode(createAccountDTO.getPassword());
        user.setPassword(hashPassword);

        // ======================================================================
        // Hiện tại do muốn validate email tồn tại nên tạo thêm cái CreateAccountDTO. Do
        // đó bị vướng cái chỗ role (ban đầu tạo 2 bảng Role và User) nên không làm code
        // này nữa)

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

        // user.setRole(this.userService.layRoleTheoTen(user.getRole().getName()));

        // ==========================================================================
        Role role = userService.layRoleTheoTen(createAccountDTO.getRoleName());
        user.setRole(role);

        this.userService.luuThongTinUser(user);

        // ✔️ Tạo, sửa, xóa thành công → dùng redirect:
        // ❌ Hiển thị form (GET) → không cần redirect
        return "redirect:/admin";

    }
    // ================================================================

}
