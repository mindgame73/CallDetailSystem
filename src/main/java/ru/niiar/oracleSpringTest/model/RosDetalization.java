package ru.niiar.oracleSpringTest.model;

import javax.persistence.*;

@Entity(name="ros_detalization")
public class RosDetalization {
    @Id
    @SequenceGenerator(name = "sequence_rosdet", sequenceName = "rosdet_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_rosdet")
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

    public RosDetalization() {
    }

    public RosDetalization(long numberA, long numberB, String dateTime, String route, int duration, double cost) {
        this.numberA = numberA;
        this.numberB = numberB;
        this.dateTime = dateTime;
        this.route = route;
        this.duration = duration;
        this.cost = cost;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public long getNumberA() {
        return numberA;
    }

    public void setNumberA(long numberA) {
        this.numberA = numberA;
    }

    public long getNumberB() {
        return numberB;
    }

    public void setNumberB(long numberB) {
        this.numberB = numberB;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }
}
