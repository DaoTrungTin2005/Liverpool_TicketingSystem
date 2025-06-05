package com.example.Liverpool_TicketSystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.Liverpool_TicketSystem.service.CustomUserDetailsService;
import com.example.Liverpool_TicketSystem.service.UserService;

@Configuration

public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    // ===========================================================================
    // ghi đè lại UserDetailsService của spring security
    // thông qua CustomUserDetailsService

    // Khi người dùng đăng nhập, Spring Security sẽ gọi bean này
    // để lấy thông tin user từ database (qua CustomUserDetailsService).

    // ta truyền UserService vì CustomUserDetailsService
    // sẽ gọi các hàm của UserService (ví dụ: getUserByEmail)
    @Bean
    public UserDetailsService userDetailsService(UserService userService) {
        return new CustomUserDetailsService(userService);
    }

    // ===========================================================================
    // Bean này giúp Spring Security xác thực user bằng thông tin từ database
    // và so sánh mật khẩu đã mã hóa

    // DaoAuthenticationProvider: Là provider mặc định của Spring Security để
    // xác thực user từ database.

    // UserDetailsService userDetailsService: Bean dùng để lấy
    // thông tin user từ database
    // (thường là class tự cài như CustomUserDetailsService (đã ghi đè lúc đầu
    // rồi)).

    @Bean
    public DaoAuthenticationProvider authProvider(
            PasswordEncoder passwordEncoder,
            UserDetailsService userDetailsService) {

        // Tạo mới provider:
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        // Khi người dùng đăng nhập,
        // provider này sẽ gọi userDetailsService.loadUserByUsername(...)
        // để lấy thông tin user từ DB
        authProvider.setUserDetailsService(userDetailsService);

        // Khi xác thực, provider sẽ dùng bean này
        // để so sánh mật khẩu nhập vào (sau khi mã hóa) với mật khẩu đã lưu trong DB.
        authProvider.setPasswordEncoder(passwordEncoder);

        // authProvider.setHideUserNotFoundExceptions(false);

        return authProvider;
    }

}
