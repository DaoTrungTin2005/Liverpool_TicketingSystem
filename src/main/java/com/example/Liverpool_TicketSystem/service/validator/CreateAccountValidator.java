package com.example.Liverpool_TicketSystem.service.validator;

import org.springframework.stereotype.Service;

import com.example.Liverpool_TicketSystem.domain.User;
import com.example.Liverpool_TicketSystem.domain.dto.CreateAccountDTO;
import com.example.Liverpool_TicketSystem.service.UserService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Service
public class CreateAccountValidator implements ConstraintValidator<RegisterChecked, CreateAccountDTO > {
    private final UserService userService;

    public CreateAccountValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(CreateAccountDTO user, ConstraintValidatorContext context) {
        boolean valid = true;

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
