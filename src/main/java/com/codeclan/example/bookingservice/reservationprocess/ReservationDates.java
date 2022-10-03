package com.codeclan.example.bookingservice.reservationprocess;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Embeddable
public class ReservationDates {
    @Column(nullable = false)
    @NotNull(message = "Check in date required")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate checkInDate;

    @Column(nullable = false)
    @NotNull(message = "Check out date required")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate checkOutDate;


    public ReservationDates() {
    }

    public ReservationDates(LocalDate checkInDate, LocalDate checkOutDate) {
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
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


    public long totalNights() {
        if (checkInDate == null || checkOutDate == null) {
            return 0;
        }
        return ChronoUnit.DAYS.between(checkInDate, checkOutDate);
    }

    public Optional<ValidationError> validate(LocalDate now) {
        if (checkInDate == null) {
            return Optional.of(new ValidationError("checkInDate.missing", "Missing check in date"));
        } else if (checkOutDate == null) {
            return Optional.of(new ValidationError("checkOutDate.missing", "Missing check out date"));
        } else if (checkInDate.isBefore(now)) {
            return Optional.of(new ValidationError("checkInDate.future", "Check in date must be in the future"));
        } else if (checkOutDate.isBefore(checkInDate)) {
            return Optional.of(new ValidationError("checkOutDate.afterCheckIn", "Check out date must occur after check in date"));
        } else if (totalNights() < 1) {
            // handles case where check in/out dates are the same.
            return Optional.of(new ValidationError("checkOutDate.minNights", "Reservation must be for at least 1 night"));
        }
        return Optional.empty();
    }

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
