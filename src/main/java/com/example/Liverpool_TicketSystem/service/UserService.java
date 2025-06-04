package com.example.Liverpool_TicketSystem.service;

import org.springframework.stereotype.Service;

import com.example.Liverpool_TicketSystem.domain.Role;
import com.example.Liverpool_TicketSystem.domain.User;
import com.example.Liverpool_TicketSystem.repository.RoleRepository;
import com.example.Liverpool_TicketSystem.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    // =========================Lưu User=========================

    // Nhận một đối tượng User làm tham số. Gọi userRepository.save(user) để lưu
    // hoặc cập nhật user vào database 
    public User luuThongTinUser(User user) {
        user = this.userRepository.save(user);
        return user;
    }

    
    // Dùng để lấy đối tượng Role (vai trò người dùng, ví dụ: "USER", "ADMIN") từ
    // database dựa vào tên role. (dùng để fix cứng role là user khi ng dùng đăng kí )
    public Role layRoleTheoTen(String name){
        return this.roleRepository.findByName(name);
    }


    public User layUserTheoEmail (String email){
        return this.userRepository.findByEmail(email) ; 
    }



}
