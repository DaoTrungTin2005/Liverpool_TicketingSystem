package com.example.Liverpool_TicketSystem.config;

import javax.swing.Spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;

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

    // Tạo một bean để cấu hình chức năng remember-me cho Spring Security khi bạn
    // dùng Spring Session (lưu session vào database)

    // SpringSessionRememberMeServices là một class của Spring Session dùng để quản
    // lý chức năng "remember-me"

    // Chức năng chính:

    // Tạo và xác thực cookie "remember-me":
    // Khi người dùng đăng nhập, class này sẽ tạo cookie remember-me và gửi về trình
    // duyệt. Nếu session hết hạn hoặc bị xoá, Spring Security sẽ kiểm tra cookie
    // này để tự động đăng nhập lại cho người dùng.

    // Kết hợp với Spring Session:
    // Khác với PersistentTokenBasedRememberMeServices (dùng với session mặc định),
    // SpringSessionRememberMeServices được thiết kế để hoạt động tốt với session
    // lưu trên database hoặc các hệ thống phân tán.

    @Bean
    public SpringSessionRememberMeServices rememberMeServices() {

        // Tạo một instance của SpringSessionRememberMeServices, class này giúp Spring
        // Security quản lý cookie "remember-me" khi dùng Spring Session.
        SpringSessionRememberMeServices rememberMeServices = new SpringSessionRememberMeServices();

        // Luôn luôn bật chức năng remember-me, không cần checkbox hoặc input
        // "remember-me" trên form. Mỗi lần đăng nhập thành công, Spring Security sẽ tự
        // động tạo cookie "remember-me".
        rememberMeServices.setAlwaysRemember(true);

        return rememberMeServices;
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

                // ===================================================================
                // 1. .sessionManagement(...): Cấu hình quản lý session
                // sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                // Luôn tạo một HTTP session mới mỗi lần người dùng truy cập.

                // Điều này đảm bảo mỗi request đều có session, kể cả khi chưa đăng nhập.

                // ✅ Ứng dụng: Dùng khi bạn cần session cho mọi request, ví dụ như lưu thông tin
                // tạm thời hoặc thống kê truy cập.

                .sessionManagement((sessionManagement) -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)

                        // invalidSessionUrl("/logout?expired")
                        // Khi session bị hết hạn (ví dụ: người dùng ngồi lâu không thao tác), họ sẽ
                        // được redirect đến URL này.

                        // URL /logout?expired thường dùng để hiển thị thông báo “phiên làm việc đã hết
                        // hạn”.
                        .invalidSessionUrl("/logout?expired")

                        // maximumSessions(1)
                        // Chỉ cho 1 session duy nhất cho mỗi người dùng.

                        // Tức là: nếu một người đã đăng nhập rồi, đăng nhập thêm ở máy khác sẽ ảnh
                        // hưởng đến session
                        .maximumSessions(1)

                        // maxSessionsPreventsLogin(false)
                        // Nếu là false: Cho phép người dùng đăng nhập mới, session cũ sẽ bị mất hiệu
                        // lực (kicked out).

                        // Nếu là true: Không cho đăng nhập mới nếu đã có 1 session đang hoạt động (dùng
                        // khi muốn bảo mật cao).

                        // ✅ Với false: Ai đăng nhập sau thì được quyền dùng, ai cũ bị đá ra
                        .maxSessionsPreventsLogin(false))

                // 2. .logout(...): Cấu hình khi người dùng đăng xuất
                // deleteCookies("JSESSIONID")
                // Khi người dùng logout, xoá cookie chứa session ID trên trình duyệt.

                // Điều này giúp xóa dấu vết phiên làm việc — tránh bị tái sử dụng.

                // invalidateHttpSession(true)
                // Khi logout, xoá luôn toàn bộ session ở server (không chỉ cookie).

                // Đảm bảo không còn dữ liệu nào tồn tại, giúp tăng bảo mật và tránh rò rỉ dữ
                // liệu cũ.

                .logout(logout -> logout.deleteCookies("JSESSIONID").invalidateHttpSession(true))
                // ===================================================================

                // Đây là cấu hình cho Spring Security để bật chức năng "Remember Me" (giữ đăng
                // nhập lâu dài).

                // Bạn chỉ định cho Spring Security sử dụng bean rememberMeServices() mà bạn đã
                // định nghĩa ở trên (trả về SpringSessionRememberMeServices).

                // Nhờ đó, mỗi lần người dùng đăng nhập thành công, Spring Security sẽ tự động
                // tạo cookie remember-me (vì bạn đã setAlwaysRemember(true)), giúp người dùng
                // không cần đăng nhập lại kể cả khi đóng trình duyệt hoặc session hết hạn
                // (trong thời gian hiệu lực của cookie).

                .rememberMe(r -> r.rememberMeServices(rememberMeServices()))

                // ===================================================================

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
