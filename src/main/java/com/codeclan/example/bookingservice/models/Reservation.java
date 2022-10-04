package com.codeclan.example.bookingservice.models;

import com.codeclan.example.bookingservice.reservationprocess.CompletedPayment;
import com.codeclan.example.bookingservice.reservationprocess.ReservationDates;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


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

//    @Column(name = "numberOfNights")
//    private int numberOfNights;

//    @Column(name = "date_from")
//    private LocalDate dateFrom;
//
//    @Column(name = "date_to")
//    private LocalDate dateTo;

    @Column(name = "total_price")
    private double totalPrice;

    @OneToOne(cascade = CascadeType.ALL)
    private CompletedPayment completedPayment;

    @Column(nullable = false)
    private LocalDateTime createdTime;

//    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JoinTable(
//            name = "reservation_guests",
//            joinColumns = @JoinColumn(name = "reservation_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "guest_id", referencedColumnName = "id")
//    )
//    private Set<Guest> guests = new HashSet<>();

    @Embedded
    @Valid
    private ReservationDates reservationDates;

    public Reservation() {
    }


    public Reservation(Pod pod, Guest guest, LocalDate dateFrom, LocalDate dateTo) {
        this.pod = pod;
        this.guest = guest;
        this.createdTime = LocalDateTime.now();
        this.reservationDates = new ReservationDates(dateFrom, dateTo);
        this.totalPrice = getTotalPrice();
    }

//    public int getNumberOfNights() {
//        return numberOfNights;
//    }
//
//    public void setNumberOfNights(int numberOfNights) {
//        this.numberOfNights = numberOfNights;
//    }

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

//    public LocalDate getDateFrom() {
//        return dateFrom;
//    }
//
//    public void setDateFrom(LocalDate dateFrom) {
//        this.dateFrom = dateFrom;
//    }
//
//    public LocalDate getDateTo() {
//        return dateTo;
//    }
//
//    public void setDateTo(LocalDate dateTo) {
//        this.dateTo = dateTo;
//    }

//    public double getTotalPrice() {
//        return totalPrice;
//    }

//    public void setTotalPrice(double totalPrice) {
//        this.totalPrice = totalPrice;
//    }

    public double getTotalPrice() {
        totalPrice = this.reservationDates.totalNights() * this.pod.getNightlyRate();
        return totalPrice;
    }

    public CompletedPayment getCompletedPayment() {
        return completedPayment;
    }

    public void setCompletedPayment(CompletedPayment completedPayment) {
        createdTime = LocalDateTime.now();
        this.completedPayment = completedPayment;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }


    public ReservationDates getDates() {
        return reservationDates;
    }

    public void setDates(ReservationDates dates) {
        this.reservationDates = dates;
    }

//    public Set<Guest> getGuests() {
//        return Collections.unmodifiableSet(guests);
//    }
//
//    public void addGuest(Guest guest) {
//        if (!pod.isOccupied()) {
//            guests.add(guest);
//        }
//    }
//
//    public void clearGuests() {
//        guests.clear();


//    public Reservation createReservation(Pod pod, int numberOfNights, Guest guest) {
//        Reservation reservation = new Reservation(numberOfNights, pod);
//        return reservation;


}
