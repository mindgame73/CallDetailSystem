package ru.niiar.cdr.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name="result_codes")
@Data
public class ResultCode implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer result_id;

    @Column(name="result_code",unique = true)
    private String resultCode;

    @Column(name="result_descr")
    private String resultDescr;

}
