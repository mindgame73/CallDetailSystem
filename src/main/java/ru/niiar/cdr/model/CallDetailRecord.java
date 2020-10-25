package ru.niiar.cdr.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name="call_detail_records")
@Getter
@Setter
public class CallDetailRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer call_id;

    @Column
    private int board_cdr_id;

    @Column(name="start_time")
    private String startTime;

    @Column(name="stop_time")
    private String stopTime;

    @Column(name="full_duration")
    private int fullTime;

    @Column(name="voice_duration")
    private int voiceTime;

    @Column(name="number_b")
    private long numberB;

    @Column(name="number_a")
    private long numberA;

    @Column
    private int mount_a;

    @Column
    private int mount_b;

    @Column(name="result_code")
    private String resultCode;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="result_id", referencedColumnName = "result_id")
    private ResultCode resultCodeObj;

    @Column(name="routing_table")
    private int routingTable;

    @Column(name="flow_port")
    private int flowPort;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="subB_id", referencedColumnName = "sub_id")
    private Subscriber subscriberB;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="subA_id", referencedColumnName = "sub_id")
    private Subscriber subscriberA;
}

