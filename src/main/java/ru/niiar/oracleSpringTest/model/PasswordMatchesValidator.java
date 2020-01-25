package ru.niiar.oracleSpringTest.model;

import ru.niiar.oracleSpringTest.dto.UserDto;
import ru.niiar.oracleSpringTest.utils.PasswordMatches;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }
    @Override
    public boolean isValid(Object o, ConstraintValidatorContext context){
        UserDto user = (UserDto) o;
        return user.getPassword().equals(user.getMatchingPassword());
    }
}
