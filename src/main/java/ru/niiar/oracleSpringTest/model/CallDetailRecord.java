package ru.niiar.oracleSpringTest.model;

import javax.persistence.*;
import java.io.Serializable;


@Entity(name="call_detail_records")
public class CallDetailRecord implements Serializable{
    @Id
    @SequenceGenerator(name = "sequence_cdr", sequenceName = "cdr_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_cdr")
    private Integer call_id;

    @Column
    private int board_cdr_id;

    @Column(name="start_time", columnDefinition = "Timestamp")
    private String startTime;

    @Column(name="stop_time", columnDefinition = "Timestamp")
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
    @JoinColumn(name="sub_id", referencedColumnName = "sub_id")
    private Subscriber subscriber;

    public CallDetailRecord(int board_cdr_id, String startTime, String stopTime, int fullTime,
                            int voiceTime, long numberB, long numberA, int mount_a, int mount_b,
                            String resultCode, ResultCode resultCodeObj, int routingTable, int flowPort,
                            Subscriber subscriber) {
        this.board_cdr_id = board_cdr_id;
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.fullTime = fullTime;
        this.voiceTime = voiceTime;
        this.numberB = numberB;
        this.numberA = numberA;
        this.mount_a = mount_a;
        this.mount_b = mount_b;
        this.resultCode = resultCode;
        this.resultCodeObj = resultCodeObj;
        this.routingTable = routingTable;
        this.flowPort = flowPort;
        this.subscriber = subscriber;
    }

    public CallDetailRecord(){}

    public Integer getCall_id() {
        return call_id;
    }

    public void setCall_id(Integer call_id) {
        this.call_id = call_id;
    }

    public int getBoard_cdr_id() {
        return board_cdr_id;
    }

    public void setBoard_cdr_id(int board_cdr_id) {
        this.board_cdr_id = board_cdr_id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStopTime() {
        return stopTime;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }

    public int getFullTime() {
        return fullTime;
    }

    public void setFullTime(int fullTime) {
        this.fullTime = fullTime;
    }

    public int getVoiceTime() {
        return voiceTime;
    }

    public void setVoiceTime(int voiceTime) {
        this.voiceTime = voiceTime;
    }

    public long getNumberB() {
        return numberB;
    }

    public void setNumberB(long numberB) {
        this.numberB = numberB;
    }

    public long getNumberA() {
        return numberA;
    }

    public void setNumberA(long numberA) {
        this.numberA = numberA;
    }

    public int getMount_a() {
        return mount_a;
    }

    public void setMount_a(int mount_a) {
        this.mount_a = mount_a;
    }

    public int getMount_b() {
        return mount_b;
    }

    public void setMount_b(int mount_b) {
        this.mount_b = mount_b;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public ResultCode getResultCodeObj() {
        return resultCodeObj;
    }

    public void setResultCodeObj(ResultCode resultCodeObj) {
        this.resultCodeObj = resultCodeObj;
    }

    public int getRoutingTable() {
        return routingTable;
    }

    public void setRoutingTable(int routingTable) {
        this.routingTable = routingTable;
    }

    public int getFlowPort() {
        return flowPort;
    }

    public void setFlowPort(int flowPort) {
        this.flowPort = flowPort;
    }

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }
}
