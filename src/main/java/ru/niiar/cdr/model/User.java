package ru.niiar.cdr.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity(name="users")
@Getter
@Setter
public class User extends BaseEntity{

    @Column(name = "user_name", length = 36, nullable = false, unique = true)
    private String userName;

    @Column(name = "encrypted_password", length = 128, nullable = false)
    private String password;

    @Column(name = "enabled", length = 1, nullable = false)
    private boolean enabled;

    @ManyToMany(cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public boolean hasRole(String roleName){
        boolean hasRole = false;
        for (Role role : roles) {
          if (role.getRoleName().equals(roleName)){
              hasRole = true;
          }
        }
        return hasRole;
    }
}
