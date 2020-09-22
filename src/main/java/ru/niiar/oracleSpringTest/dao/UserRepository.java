package ru.niiar.oracleSpringTest.dao;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.niiar.oracleSpringTest.model.User;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends CrudRepository<User,Integer> {
    Optional<User> findUserByUserNameAndEnabledTrue(String username);
    Optional<User> findUserByUserName(String username);
    List<User> findAllByOrderByEnabledDesc();
}
