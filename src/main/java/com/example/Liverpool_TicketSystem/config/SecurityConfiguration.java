package com.example.Liverpool_TicketSystem.config;

import javax.swing.Spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.example.Liverpool_TicketSystem.service.CustomUserDetailsService;
import com.example.Liverpool_TicketSystem.service.UserService;

import jakarta.servlet.DispatcherType;

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

    // ===========================================================================
    // Tạo một Bean AuthenticationSuccessHandler dùng để xử lý hành động khi người
    // dùng đăng nhập thành công.

    // AuthenticationSuccessHandler: là interface của Spring Security, định nghĩa
    // cách xử lý sau khi người dùng đăng nhập thành công.

    // CustomSuccessHandler là class bạn viết để tùy chỉnh việc chuyển hướng người
    // dùng dựa trên vai trò (role) sau khi đăng nhập thành công (ví dụ: chuyển
    // admin đến /admin, user đến /).
    @Bean
    public AuthenticationSuccessHandler customSuccessHandler(UserService userService) {
        return new CustomSuccessHandler(userService);
    }

    // ===========================================================================

    // SecurityFilterChain để Spring Security biết cách bảo vệ các URL,
    // cấu hình đăng nhập, phân quyền, logout, CSRF, v.v.

    // Ví dụ:
    // .authorizeHttpRequests(...): Cấu hình quyền truy cập cho từng URL.
    // .formLogin(...): Cấu hình đăng nhập bằng form.
    // .logout(...): Cấu hình logout.
    // .csrf(...): Cấu hình CSRF.

    // Tham số HttpSecurity http cho phép bạn cấu hình các rule bảo mật
    // bằng Java code (method chaining).

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, UserService userService) throws Exception {

        // http.authorizeHttpRequests(...): Bắt đầu cấu hình các quy tắc
        // phân quyền cho các request HTTP.
        http
                .authorizeHttpRequests(authorize -> authorize

                        // Không có cái này dô localhost:8080/ nó báo lỗi (không load được view)
                        .dispatcherTypeMatchers(DispatcherType.FORWARD,
                                DispatcherType.INCLUDE)
                        .permitAll()

                        // Nếu để dòng này thì dô bất kỳ trang nào cũng không cần đăng nhập
                        // .anyRequest().permitAll())

                        // Cho phép truy cập các thư mục chỉ định, còn lại thì phải signin
                        .requestMatchers("/signin", "/signup", "client/css/**", "client/js/**",
                                "client/images/**")
                        .permitAll()

                        // Có ROlE là admin thì mới được truy cập các URL bắt đầu bằng /admin/
                        // Còn ROLE là user thì khi truy cập nó báo về lỗi 403 Forbidden
                        // Sẽ làm 1 cái trang để hiển thị lỗi 403 này
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        .anyRequest().authenticated())

                // formLogin(...): Cấu hình đăng nhập bằng form.
                .formLogin(formLogin -> formLogin

                        // Sử dụng trang đăng nhập custom tại /signin (thay vì trang mặc định)
                        // Đổi thành /signin ứng vs action="/signin" trong signin.jsp
                        // Lúc này, Spring Security sẽ hiển thị trang /signin cho người dùng
                        .loginPage("/signin")

                        // Nếu đăng nhập sai, chuyển hướng về /signin?error
                        .failureUrl("/signin?error")

                        // .successHandler(...) là method của Spring Security dùng để đăng ký một
                        // AuthenticationSuccessHandler — tức là một đối tượng xử lý hành động sau khi
                        // đăng nhập thành công.

                        // customSuccessHandler(userService) gọi tới method tạo bean
                        // AuthenticationSuccessHandler mà bạn định nghĩa (trong class
                        // SecurityConfiguration), trả về đối tượng CustomSuccessHandler.

                        // Khi người dùng đăng nhập thành công, Spring Security sẽ gọi phương thức
                        // onAuthenticationSuccess(...) trong CustomSuccessHandler để thực hiện các
                        // logic tùy chỉnh (ví dụ: chuyển hướng người dùng đến trang phù hợp theo role,
                        // ghi log, cập nhật trạng thái user, v.v).

                        .successHandler(customSuccessHandler(userService))

                        // Spring Security mặc định chuyển về / sau khi đăng nhập thành công
                        // Nếu muốn đổi : .defaultSuccessUrl("/home", true) // <-- Thêm dòng này

                        // .permitAll(): Ai cũng truy cập được trang đăng nhập.
                        .permitAll())

                // Cấu hình trang hiển thị khi người dùng bị từ chối truy cập (HTTP 403 -
                // Forbidden) vì không đủ quyền (role).
                .exceptionHandling(ex -> ex.accessDeniedPage("/access-denied"));
        return http.build();
    }

}
