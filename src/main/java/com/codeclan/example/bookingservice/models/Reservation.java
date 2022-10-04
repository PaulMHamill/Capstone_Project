package com.codeclan.example.bookingservice.models;

import com.codeclan.example.bookingservice.reservationprocess.CompletedPayment;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;


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

    @Column
    @NotNull(message = "Check in date required")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate checkInDate;

    @Column
    @NotNull(message = "Check out date required")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate checkOutDate;

    @Column(name = "total_price")
    private double totalPrice;

    @OneToOne(cascade = CascadeType.ALL)
    private CompletedPayment completedPayment;

    @Column
    private LocalDateTime createdTime;

//    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JoinTable(
//            name = "reservation_guests",
//            joinColumns = @JoinColumn(name = "reservation_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "guest_id", referencedColumnName = "id")
//    )
//    private Set<Guest> guests = new HashSet<>();


    public Reservation() {
    }


    public Reservation(Pod pod, Guest guest, LocalDate checkInDate, LocalDate checkOutDate) {
        this.pod = pod;
        this.guest = guest;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.createdTime = LocalDateTime.now();
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

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public long totalNights() {
        if (checkInDate == null || checkOutDate == null) {
            return 0;
        }
        return ChronoUnit.DAYS.between(checkInDate, checkOutDate);
    }

    public double getTotalPrice() {
        totalPrice = this.totalNights() * this.pod.getNightlyRate();
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


//    public Optional<ReservationDates.ValidationError> validate(LocalDate now) {
//        if (checkInDate == null) {
//            return Optional.of(new ReservationDates.ValidationError("checkInDate.missing", "Missing check in date"));
//        } else if (checkOutDate == null) {
//            return Optional.of(new ReservationDates.ValidationError("checkOutDate.missing", "Missing check out date"));
//        } else if (checkInDate.isBefore(now)) {
//            return Optional.of(new ReservationDates.ValidationError("checkInDate.future", "Check in date must be in the future"));
//        } else if (checkOutDate.isBefore(checkInDate)) {
//            return Optional.of(new ReservationDates.ValidationError("checkOutDate.afterCheckIn", "Check out date must occur after check in date"));
//        } else if (totalNights() < 1) {
//            // handles case where check in/out dates are the same.
//            return Optional.of(new ReservationDates.ValidationError("checkOutDate.minNights", "Reservation must be for at least 1 night"));
//        }
//        return Optional.empty();
//    }

    public static class ValidationError {
        private String code;
        private String reason;

        public ValidationError(String code, String reason) {
            this.code = code;
            this.reason = reason;
        }

        public String getCode() {
            return code;
        }

        public String getReason() {
            return reason;
        }
    }

    @Override
    public String toString() {
        return "ReservationDates{" +
                "checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                '}';
    }

}
