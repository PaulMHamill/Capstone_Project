package models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

    @Entity
    public class House implements Serializable {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        @Column(nullable = false)
        private String name;

        @Column(nullable = false)
        private String email;

        @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
        // Stop bidirectional relationship which cause a cycle.
        @JsonIgnore

        @Column(nullable = false)
        private LocalTime earliestCheckInTime;

        @Column(nullable = false)
        private LocalTime latestCheckInTime;

        @Column(nullable = false)
        private LocalTime standardCheckOutTime;

        @Column(nullable = false)
        private LocalTime latestCheckOutTime;

        @Column(nullable = false)
        private BigDecimal lateCheckoutFee;

        private final static LocalTime DEFAULT_EARLIEST_CHECK_IN = LocalTime.of(7, 0);
        private final static LocalTime DEFAULT_LATEST_CHECK_IN = LocalTime.of(22, 0);
        private final static LocalTime DEFAULT_STANDARD_CHECKOUT = LocalTime.of(11, 0);
        private final static LocalTime DEFAULT_LATEST_CHECKOUT = LocalTime.of(22, 0);
        private final static BigDecimal DEFAULT_LATE_CHECKOUT_FEE = BigDecimal.valueOf(15.95);

        public House(String name, String email) {
            this(name, email,
                    DEFAULT_EARLIEST_CHECK_IN,
                    DEFAULT_LATEST_CHECK_IN,
                    DEFAULT_STANDARD_CHECKOUT,
                    DEFAULT_LATEST_CHECKOUT,
                    DEFAULT_LATE_CHECKOUT_FEE);
        }

        public House(String name, String email,
                     LocalTime earliestCheckInTime,
                     LocalTime latestCheckInTime,
                     LocalTime standardCheckOutTime,
                     LocalTime latestCheckOutTime,
                     BigDecimal lateCheckoutFee) {
            this.name = name;
            this.email = email;
            this.earliestCheckInTime = earliestCheckInTime;
            this.latestCheckInTime = latestCheckInTime;
            this.standardCheckOutTime = standardCheckOutTime;
            this.latestCheckOutTime = latestCheckOutTime;
            this.lateCheckoutFee = lateCheckoutFee;
        }

        public House() {
        }
        public void addGuest(Guest guest) {
            house.add(guest);
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public LocalTime getEarliestCheckInTime() {
            return earliestCheckInTime;
        }

        public void setEarliestCheckInTime(LocalTime earliestCheckInTime) {
            this.earliestCheckInTime = earliestCheckInTime;
        }

        public LocalTime getLatestCheckInTime() {
            return latestCheckInTime;
        }

        public void setLatestCheckInTime(LocalTime latestCheckInTime) {
            this.latestCheckInTime = latestCheckInTime;
        }

        public LocalTime getStandardCheckOutTime() {
            return standardCheckOutTime;
        }

        public void setStandardCheckOutTime(LocalTime standardCheckOutTime) {
            this.standardCheckOutTime = standardCheckOutTime;
        }

        public LocalTime getLatestCheckOutTime() {
            return latestCheckOutTime;
        }

        public void setLatestCheckOutTime(LocalTime latestCheckOutTime) {
            this.latestCheckOutTime = latestCheckOutTime;
        }

        public BigDecimal getLateCheckoutFee() {
            return lateCheckoutFee;
        }

        public void setLateCheckoutFee(BigDecimal lateCheckoutFee) {
            this.lateCheckoutFee = lateCheckoutFee;
        }

        /**
         * Generates range of allowable check in times spanning from the earliest to latest times for the holiday home.
         * <p>This provides an estimated check in time to help staff organise the room.</p>
         */
        public List<LocalTime> allowableCheckInTimes() {
            long span = ChronoUnit.HOURS.between(earliestCheckInTime, latestCheckInTime) + 1;

            return Stream.iterate(earliestCheckInTime, time -> time.plusHours(1))
                    .limit(span)
                    .collect(Collectors.toList());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            House house = (House) o;
            return Objects.equals(name, house.name) &&
                    Objects.equals(email, house.email);
        }

        @Override
        public int hashCode() {

            return Objects.hash(name, email);
        }

        @Override
        public String toString() {
            return "Hotel{" +
                    "id=" + id +
                    ", address=" + address +
                    ", email='" + email + '\'' +
                    '}';
        }
    }

}
