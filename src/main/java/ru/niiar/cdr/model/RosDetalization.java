package ru.niiar.cdr.model;

import lombok.Data;

import javax.persistence.*;

@Entity(name="ros_detalization")
@Data
public class RosDetalization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private long numberA;

    private long numberB;

    @Column(name="date_time", columnDefinition = "Timestamp")
    private String dateTime;

    private String route;

    private int duration;

    private double cost;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="sub_id", referencedColumnName = "sub_id")
    private Subscriber subscriber;

}
