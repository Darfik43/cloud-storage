package com.darfik.cloudstorage.web.validation;

import com.darfik.cloudstorage.domain.user.RegistrationRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        RegistrationRequest registrationRequest = (RegistrationRequest) obj;
        if (!(registrationRequest.password().equals(registrationRequest.matchingPassword()))) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("matchingPassword").addConstraintViolation();
            return false;
        }
        return true;
    }

}
