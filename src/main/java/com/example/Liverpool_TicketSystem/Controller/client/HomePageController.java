package com.example.Liverpool_TicketSystem.Controller.client;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.Liverpool_TicketSystem.domain.User;
import com.example.Liverpool_TicketSystem.domain.dto.RegisterDTO;
import com.example.Liverpool_TicketSystem.service.UserService;

import jakarta.validation.Valid;

@Controller
public class HomePageController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public HomePageController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    // ===============================Trang Chủ==============================
    @GetMapping("/")
    public String getHomePage() {
        return "client/homepage/show";

    }

    // ==================================Đăng Kí=============================
    @GetMapping("/signup")
    public String getSignUpPage(Model model) {
        // truyền vào view cái DTO
        model.addAttribute("signUpUser", new RegisterDTO());
        return "client/auth/signup";
    }

    // khi bấm nút đăng ký, sẽ gửi dữ liệu về đây
    @PostMapping("/signup")

    // Spring sẽ tự động lấy dữ liệu từ form (có tên các trường trùng với thuộc tính
    // của RegisterDTO) và gán vào đối tượng registerDTO

    // @Valid để kích hoạt xác thực dữ liệu (bao gồm xác thực xem Pass và
    // ConfirmPass có giống hay ko, email , not null ....),

    // BindingResult để kiểm tra lỗi xác thực (trả về true / false)
    public String handleSignUp(@ModelAttribute("signUpUser") @Valid RegisterDTO registerDTO,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            // Nếu có lỗi trong quá trình xác thực, trả về trang đăng ký với thông báo lỗi

            // Xài return "redirect:/signup"; thì Không giữ lại thông báo lỗi và Mất dữ liệu
            // nhập

            // Xài return "client/auth/signup"; thì các lỗi validation sẽ được giữ lại, Giữ
            // lại dữ liệu nhập
            return "client/auth/signup";
        }

        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setEmail(registerDTO.getEmail());

        String hashpassword = this.passwordEncoder.encode(registerDTO.getPassword());
        user.setPassword(hashpassword);

        user.setRole(this.userService.layRoleTheoTen("USER"));

        this.userService.luuThongTinUser(user);
        return "redirect:/signin"; // chuyển hướng đến trang đăng nhập sau khi đăng ký thành công
    }

    // ===============================Đăng Nhập==============================
    @GetMapping("/signin")
    public String getSignInPage() {
        return "client/auth/signin";
    }

    // ===============================Trang Từ Chối==============================
    @GetMapping("/access-denied")
    public String getDenyPage(Model model) {
        return "client/auth/deny";
    }

}
