package ru.niiar.cdr.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name="subscribers")
@Getter
@Setter
public class Subscriber implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sub_id;

    @Column(name="full_Name")
    private String fullName;

    @Column(name="internal_num")
    private Integer internalNum;

    @Column(name="external_num")
    private Long externalNum;

    @Column
    private String building;

    @Column
    private String room;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="division_id", referencedColumnName = "division_id")
    private Division division;

    @Column(name="sub_descr")
    private String subDescr;

    @Column(name="gp_strip")
    private String gpStrip;

    @Column
    private String allocation;

    @Column(name="is_digital")
    private boolean isDigital;

    @Column
    private String cos;

    @Column(name="is_fax")
    private boolean isFax;

    @Column(name="is_sip")
    private boolean isSip;
}
