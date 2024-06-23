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
        boolean isValid = passwordsMatch(registrationRequest.password(), registrationRequest.matchingPassword());

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("matchingPassword").addConstraintViolation();
        }

        return isValid;
    }

    private boolean passwordsMatch(String password, String matchingPassword) {
        return password.equals(matchingPassword);
    }

}
