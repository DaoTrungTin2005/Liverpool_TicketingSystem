package com.example.Liverpool_TicketSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Liverpool_TicketSystem.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    // Khai báo một phương thức tên save nhận vào một đối tượng User và trả về một
    // đối tượng User.
    User save(User user);

    User findByEmail(String email) ;

    // để kiểm tra coi email đã tồn tại trong database hay chưa
    // Nếu tồn tại thì trả về true, nếu không thì trả về false
    boolean existsByEmail(String email) ;
    
}
