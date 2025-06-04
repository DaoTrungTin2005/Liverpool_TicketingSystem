package com.example.Liverpool_TicketSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.example.Liverpool_TicketSystem.domain.Role;

@Service
public interface RoleRepository extends JpaRepository<Role, Long> {
    //tìm một đối tượng Role theo giá trị trường name.
    Role findByName(String name);
}


