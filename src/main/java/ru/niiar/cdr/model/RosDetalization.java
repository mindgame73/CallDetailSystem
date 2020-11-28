package ru.niiar.cdr.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity(name="ros_detalizations")
@Data
public class RosDetalization extends BaseEntity {

    private long numberA;

    private long numberB;

    @Column(name="date_time")
    private LocalDateTime dateTime;

    private String route;

    private int duration;

    private double cost;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="sub_id", referencedColumnName = "id")
    private Subscriber subscriber;

}
