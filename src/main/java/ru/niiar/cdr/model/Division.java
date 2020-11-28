package ru.niiar.cdr.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Entity(name="divisions")
@Data
public class Division extends BaseEntity implements Serializable {

    @Column(name="division_name",unique = true)
    private String divisionName;

}
