package com.example.Liverpool_TicketSystem.service;

import java.util.Collections;

import javax.swing.Spring;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.Liverpool_TicketSystem.domain.User;

// UserDetailsService là một interface của Spring Security 
// dùng để tải thông tin người dùng từ database hoặc nguồn khác khi xác thực (login).

public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override

    // Khi xác thực thành công, Spring Security
    // sẽ lưu thông tin người dùng dưới dạng UserDetails.
    // Nó chứa các thông tin như: username, password,
    // danh sách quyền (roles/authorities), trạng thái tài khoản, v.v.

    // loadUserByUsername là phương thức duy nhất của interface UserDetailsService
    // trong Spring Security.
    // Khi người dùng đăng nhập, Spring Security sẽ gọi phương thức này
    // và truyền vào giá trị username (hoặc email) mà người dùng nhập.

    // UsernameNotFoundException là exception của Spring Security.
    // Được ném ra trong phương thức loadUserByUsername khi
    // không tìm thấy người dùng với username (hoặc email) được truyền vào.

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // Check user có tồn tại hay không bằng email (vì khi login ta nhập email vômà)
        // phải DI cái UserService vào đây để gọi phương thức layUserTheoEmail
        User user = this.userService.layUserTheoEmail(email);

        if (user == null) {
            // Nếu không tìm thấy user, ném ra exception
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        // Nếu tìm thấy user, trả về một đối tượng UserDetails
        // Tạo User của Spring Security để Spring Security có thể xác thực
        // và phân quyền user một cách chuẩn xác, an toàn và dễ mở rộng.
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),

                // Nếu bạn chỉ để "ADMIN" mà không có "ROLE_", Spring Security sẽ không nhận
                // diện đúng vai trò và user sẽ không được phân quyền như mong muốn.
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().getName())));

    }

}
