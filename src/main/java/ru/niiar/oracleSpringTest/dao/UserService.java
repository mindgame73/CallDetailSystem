package ru.niiar.oracleSpringTest.dao;

import ru.niiar.oracleSpringTest.dto.UserDto;
import ru.niiar.oracleSpringTest.exception.UserAlreadyExistException;
import ru.niiar.oracleSpringTest.model.User;

public interface UserService {
    User registerUser(UserDto userDto) throws UserAlreadyExistException;
}
