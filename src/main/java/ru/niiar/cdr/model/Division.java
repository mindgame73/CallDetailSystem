package ru.niiar.cdr.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name="divisions")
@Data
public class Division implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer division_id;

    @Column(name="division_name",unique = true)
    private String divisionName;

}
