package ru.niiar.cdr.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Entity(name="result_codes")
@Data
public class ResultCode extends BaseEntity implements Serializable {

    @Column(name="result_code",unique = true)
    private String resultCode;

    @Column(name="result_descr")
    private String resultDescr;

}
