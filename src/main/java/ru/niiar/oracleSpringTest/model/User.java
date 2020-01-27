package ru.niiar.oracleSpringTest.model;


import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Entity(name="users")
public class User{
    @Id
    @SequenceGenerator(name = "sequence_user", sequenceName = "user_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_user")
    private Integer user_id;

    @Column(name = "user_name", length = 36, nullable = false, unique = true)
    private String userName;

    @Column(name = "encrypted_password", length = 128, nullable = false)
    private String password;

    @Column(name = "enabled", length = 1, nullable = false)
    private boolean enabled;

    @ManyToMany(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public User() {
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

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
