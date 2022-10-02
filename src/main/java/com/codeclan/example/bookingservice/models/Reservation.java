package com.codeclan.example.bookingservice.models;

import com.codeclan.example.bookingservice.reservationprocess.CompletedPayment;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
public class Reservation {

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

    @Column(name = "date_from")
    private LocalDate dateFrom;

    @Column(name = "date_to")
    private LocalDate dateTo;

    @Column(name = "total_price")
    private double totalPrice;

    @OneToOne(cascade = CascadeType.ALL)
    private CompletedPayment completedPayment;

    @Column(nullable = false)
    private LocalDateTime createdTime;

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public Reservation() {
    }


    public Reservation(int numberOfNights, Pod pod, Guest guest, LocalDate dateFrom, LocalDate dateTo) {
        this.numberOfNights = numberOfNights;
        this.pod = pod;
        this.guest = guest;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
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

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getTotalForStay() {
        return this.numberOfNights * this.pod.getNightlyRate();
    }

    public CompletedPayment getCompletedPayment() {
        return completedPayment;
    }

    public void setCompletedPayment(CompletedPayment completedPayment) {
        createdTime = LocalDateTime.now();
        this.completedPayment = completedPayment;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }


//    public Reservation createReservation(Pod pod, int numberOfNights, Guest guest) {
//        Reservation reservation = new Reservation(numberOfNights, pod);
//        return reservation;

}
