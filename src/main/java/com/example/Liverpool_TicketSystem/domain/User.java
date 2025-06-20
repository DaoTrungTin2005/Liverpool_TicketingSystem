package com.example.Liverpool_TicketSystem.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

// Đánh dấu lớp là một thực thể JPA
@Entity

// chỉ định rõ tên bảng trong database là users

// Nếu không dùng @Table(name = "users") thì mặc định JPA sẽ lấy tên bảng là
// user (theo tên class, viết thường).
@Table(name = "users")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String username;

    // 1 role có nhiều user sử dụng (nhiều user có thể có cùng 1 role)
    // 1 user chỉ có 1 role

    // Nếu bạn dùng @OneToOne thay vì @ManyToOne, JPA sẽ không cho phép nhiều user
    // cùng 1 role, và có thể gây lỗi khi insert dữ liệu.

    @ManyToOne

    // Chỉ định cột role_id trong bảng users là khóa ngoại liên kết tới bảng roles.
    @JoinColumn(name = "role_id")
    private Role role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", email=" + email + ", password=" + password + ", username=" + username + ", role="
                + role + "]";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
