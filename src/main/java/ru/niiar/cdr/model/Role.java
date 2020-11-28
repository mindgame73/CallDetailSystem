package ru.niiar.cdr.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity(name="roles")
@Data
public class Role extends BaseEntity{

    @Column(name = "role_name", length = 30, nullable = false)
    private String roleName;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private Set<User> users;
}
