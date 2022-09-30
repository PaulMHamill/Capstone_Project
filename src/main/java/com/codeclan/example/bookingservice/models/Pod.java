package com.codeclan.example.bookingservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pods")
public class Pod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String podName;

    @Column
    private PodType type;

    @Column
    private double nightlyRate;

    @OneToMany(mappedBy = "pod")
    @JsonIgnore
    private List<Reservation> reservations;

    public Pod(){
    }

    public Pod(String podName, PodType podType, double nightlyRate) {
        this.podName = podName;
        this.type = podType;
        this.nightlyRate = nightlyRate;
        this.reservations = new ArrayList<>();
    }

    public String getPodName() {
        return podName;
    }

    public void setPodName(String podName) {
        this.podName = podName;
    }

    public double getNightlyRate() {
        return nightlyRate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PodType getType() {
        return type;
    }

    public void setType(PodType type) {
        this.type = type;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public void setNightlyRate(double nightlyRate) {
        this.nightlyRate = nightlyRate;
    }
}
