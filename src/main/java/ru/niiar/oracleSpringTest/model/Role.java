package ru.niiar.oracleSpringTest.model;

import javax.persistence.*;
import java.util.Set;

@Entity(name="roles")
public class Role {
    @Id
    private Integer role_id;

    @Column(name = "role_name", length = 30, nullable = false)
    private String roleName;

    public Role() {
    }

    public Role(String roleName){
        this.roleName = roleName;
    }

    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private Set<User> users;

    public Integer getRole_id() {
        return role_id;
    }

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
