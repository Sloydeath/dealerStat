package com.leverx.validation;

import com.leverx.annotations.PasswordMatches;
import com.leverx.model.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        User user = (User) obj;
        return true;
    }
//    user.getPassword().equals(user.getMatchingPassword());
}
