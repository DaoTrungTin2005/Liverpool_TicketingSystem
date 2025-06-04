package com.example.Liverpool_TicketSystem.service.validator;

import org.springframework.stereotype.Service;

import com.example.Liverpool_TicketSystem.domain.dto.RegisterDTO;
import com.example.Liverpool_TicketSystem.service.UserService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Service

// Khi bạn gắn @RegisterChecked lên class RegisterDTO và dùng @Valid trong
// controller, Spring sẽ tự động gọi phương thức isValid(...) trong
// RegisterValidator để kiểm tra dữ liệu hợp lệ.
public class RegisterValidator implements ConstraintValidator<RegisterChecked, RegisterDTO> {
    private final UserService userService;

    public RegisterValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(RegisterDTO user, ConstraintValidatorContext context) {
        boolean valid = true;

        // Check if password fields match
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            context.buildConstraintViolationWithTemplate("Incorrect password")
                    .addPropertyNode("confirmPassword")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
            valid = false;
        }

        // Additional validations can be added here

        // check email tồn tại hay chưa
        if (this.userService.kiemTraEmailTonTai(user.getEmail())) {
            context.buildConstraintViolationWithTemplate("Email already exists.")
                    .addPropertyNode("email")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
            valid = false;
        }
        return valid;
    }
}
