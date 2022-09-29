package com.codeclan.example.bookingservice.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "reservations")
public class Reservation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "guest_id")
    private Guest guest;

    @ManyToOne
    @JoinColumn(name = "pod_id")
    private Pod pod;

    @Column(name = "numberOfNights")
    private int numberOfNights;

    public Reservation() {
    }


    public Reservation(int numberOfNights, Pod pod, Guest guest) {
        this.numberOfNights = numberOfNights;
        this.pod = pod;
        this.guest = guest;
    }

    public int getNumberOfNights() {
        return numberOfNights;
    }

    public void setNumberOfNights(int numberOfNights) {
        this.numberOfNights = numberOfNights;
    }

    public Pod getPod() {
        return pod;
    }

    public void setPod(Pod pod) {
        this.pod = pod;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }


    public double getTotalForStay() {
        return this.numberOfNights * this.pod.getNightlyRate();
    }

//    public Reservation createReservation(Pod pod, int numberOfNights, Guest guest) {
//        Reservation reservation = new Reservation(numberOfNights, pod);
//        return reservation;

}
