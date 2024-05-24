package com.darfik.cloudstorage.security;

import com.darfik.cloudstorage.user.AppUserRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        AppUserRequest appUserRequest = (AppUserRequest) obj;
        if (!(appUserRequest.getPassword().equals(appUserRequest.getMatchingPassword()))) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("matchingPassword").addConstraintViolation();
            return false; // Second option is to delete all code above
        }
        return true;
    }
}
