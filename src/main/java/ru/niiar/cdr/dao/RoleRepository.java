package ru.niiar.cdr.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.niiar.cdr.model.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role,Integer> {
    Role findRoleByRoleName(String roleName);
}
