package ru.niiar.cdr.dao;

import ru.niiar.cdr.dto.UserDto;
import ru.niiar.cdr.exception.UserAlreadyExistException;
import ru.niiar.cdr.model.User;

public interface UserService {
    User registerUser(UserDto userDto) throws UserAlreadyExistException;
}
