package com.darfik.cloudstorage.web.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.passay.*;

import java.util.Arrays;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
    }

    @Override
    public boolean isValid(String password,
                           ConstraintValidatorContext constraintValidatorContext) {
        PasswordValidator validator = new PasswordValidator(Arrays.asList(
                new CharacterCharacteristicsRule(3, Arrays.asList(
                        new CharacterRule(EnglishCharacterData.UpperCase, 1),
                        new CharacterRule(EnglishCharacterData.LowerCase, 1),
                        new CharacterRule(EnglishCharacterData.Digit, 1),
                        new CharacterRule(EnglishCharacterData.Special, 1)
                )),
                new LengthRule(8, 30)
        )
        );

        RuleResult result = validator.validate(new PasswordData(password));
        return result.isValid();
    }


//    Could be implemented with RegExp

//    @Override
//    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
//        String lowercaseRule = "(?=.*[a-z])";
//        String uppercaseRule = "(?=.*[A-Z])";
//        String digitRule = "(?=.*\\d)";
//        String specialCharRule = "(?=.*[@$!%*?&])";
//        String lengthRule = "[A-Za-z\\d@$!%*?&]{8,30}$";
//
//        String passwordPattern = "^" + lowercaseRule + uppercaseRule + digitRule + specialCharRule + lengthRule;
//
//        boolean isValid = password.matches(passwordPattern);
//
//        if (!isValid) {
//            constraintValidatorContext.buildConstraintViolationWithTemplate(
//                    "Password must be between 8 and 30 characters, and contain at least one uppercase letter, one lowercase letter, one digit, and one special character.")
//                    .addConstraintViolation();
//        }
//
//        return isValid;
//    }

}
