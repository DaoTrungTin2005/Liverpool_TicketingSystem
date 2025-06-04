package com.example.Liverpool_TicketSystem.domain.dto;

import com.example.Liverpool_TicketSystem.service.validator.RegisterChecked;

@RegisterChecked
public class RegisterDTO {
    private String username; 
    private String email;
    private String password;
    private String confirmPassword;
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
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
    public String getConfirmPassword() {
        return confirmPassword;
    }
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }


    
}
