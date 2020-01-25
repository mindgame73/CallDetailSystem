package ru.niiar.oracleSpringTest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.niiar.oracleSpringTest.dao.RoleRepository;
import ru.niiar.oracleSpringTest.dao.UserRepository;
import ru.niiar.oracleSpringTest.dao.UserService;
import ru.niiar.oracleSpringTest.dto.UserDto;
import ru.niiar.oracleSpringTest.exception.UserAlreadyExistException;
import ru.niiar.oracleSpringTest.model.Role;
import ru.niiar.oracleSpringTest.model.User;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Transactional
    @Override
    public User registerUser(UserDto userDto) throws UserAlreadyExistException {
        if (isUserExist(userDto.getUsername())){
            throw new UserAlreadyExistException(String.format("Пользователь %s уже зарегистрирован в системе.",
                    userDto.getUsername()));
        }
        else
        {
            User user = new User();
            Set<Role> role = new HashSet<>();
            role.add(roleRepository.findRoleByRoleName("ROLE_USER"));
            user.setUserName(userDto.getUsername());
            user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
            user.setEnabled(false);
            user.setRoles(role);
            return userRepository.save(user);
        }
    }

    private boolean isUserExist(String username){
       return userRepository.findUserByUserName(username).isPresent();
    }
}
