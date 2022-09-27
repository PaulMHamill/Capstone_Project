package models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private UUID reservationId = UUID.randomUUID();

    @OneToOne(mappedBy = "reservation")
    private House house;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "reservation_guests",
            joinColumns = @JoinColumn(name = "reservation_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "guest_id", referencedColumnName = "id")
    )
    private Set<Guest> guests = new HashSet<>();

    @Embedded
    @Valid
    private ReservationDates dates = new ReservationDates();

    @ManyToMany
    @JoinTable(
            name = "reservation_general_extras",
            joinColumns = @JoinColumn(name = "reservation_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "general_extra_id", referencedColumnName = "id")
    )
    private Set<Extra> generalExtras = new HashSet<>();

    // List because the UI should display plans by Guest order.

    @OneToOne(cascade = CascadeType.ALL)
    private CompletedPayment completedPayment;

    @Column(nullable = false)
    private LocalDateTime createdTime;

    /**
     * @return The time this {@code Reservation} was successfully paid for and persisted.
     */
    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public Reservation() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
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

    /**
     * Use the utility functions add/remove guest to perform changes.
     *
     * @return The unmodifiable set of {@code Guest}s.
     */
    public Set<Guest> getGuests() {
        return Collections.unmodifiableSet(guests);
    }

    /**
     * Add a guest only if the room has free beds.
     *
     * @param guest The guest to add.
     */
    public void addGuest(Guest guest) {
        if (!isRoomFull()) {
            guests.add(guest);
        }
    }

    public void clearGuests() {
        guests.clear();
    }

    /**
     * Allows UI to easily remove a {@code Guest} in the 'add guest' page. Its easier for the UI to 'POST' a guest id
     * rather than provide the full {@code Guest} details that match {@code Guest.equals/hashCode}.
     *
     * @param guestId The temporarily assigned guest id.
     * @return {@code true} if the {@code Guest} was removed otherwise {@code false}.
     */
    public boolean removeGuestById(UUID guestId) {
        return guests.removeIf(guest -> guest.getTempId().equals(guestId));
    }

    public UUID getReservationId() {
        return reservationId;
    }

    public Set<Extra> getGeneralExtras() {
        return generalExtras;
    }

    /**
     * All {@code Extra}s must be {@code Extra.Category.General} otherwise an IllegalArgumentException is thrown.
     *
     * @param generalExtras All {@code Extra}s must be {@code Extra.Category.General}.
     * @throws IllegalArgumentException
     */
    public void setGeneralExtras(Set<Extra> generalExtras) throws IllegalArgumentException {
        boolean containsInvalidCategories
                = generalExtras.stream().anyMatch(extra -> extra.getCategory() != Extra.Category.General);
        if (containsInvalidCategories) {
            throw new IllegalArgumentException("Contains invalid categories that are not Extra.Category.General");
        }
        this.generalExtras = generalExtras;
    }


    public ReservationDates getDates() {
        return dates;
    }

    public void setDates(ReservationDates dates) {
        this.dates = dates;
    }

    public boolean isRoomFull() {
        return guests.size() >= room.getBeds();
    }

    public boolean hasGuests() {
        return !guests.isEmpty();
    }

    public boolean hasAtLeastOneAdultGuest() {
        return guests.stream().anyMatch(guest -> !guest.isChild());
    }

    /**
     * Calculates {@code Extra.Type} to correctly charge the food and general extras.
     *
     * @return Depending on the room type, return {@code Extra.Type.Premium/Basic}.
     */
    public Extra.Type getExtraPricingType() {
        switch (room.getRoomType()) {
            case Luxury:
            case Business:
                return Extra.Type.Premium;
            default:
                return Extra.Type.Basic;
        }
    }

    /**
     * Calculates the chargeable late fee price only if the user has selected the late checkout option.
     */
    public BigDecimal getChargeableLateCheckoutFee() {
        return dates.isLateCheckout() ? getLateCheckoutFee() : BigDecimal.ZERO;
    }

    /**
     * The late checkout fee depending on the type of room.
     * For the actual chargeable fee, use {@link #getChargeableLateCheckoutFee()}
     */
    public BigDecimal getLateCheckoutFee() {
        switch (room.getRoomType()) {
            case Luxury:
            case Business:
                return BigDecimal.ZERO;
            default:
                return room.getHotel().getLateCheckoutFee();
        }
    }

    /**
     * No late fee is considered.
     * Provided separately to allow break down to sub totals on invoices.
     *
     * @return Total nights * per night cost
     */
    public BigDecimal getTotalRoomCost() {
        long nights = dates.totalNights();
        if (nights == 0) {
            return BigDecimal.ZERO;
        }
        return House.getCostPerNight().multiply(BigDecimal.valueOf(nights));
    }

    /**
     * Provided separately to allow break down to sub totals on invoices.
     *
     * @return {@link #getTotalRoomCost} + {@link #getChargeableLateCheckoutFee}
     */
    public BigDecimal getTotalRoomCostWithLateCheckoutFee() {
        return getTotalRoomCost().add(getChargeableLateCheckoutFee());
    }

    /**
     * Calculates the total general extras cost.
     * Provided separately to allow break down to sub totals on invoices.
     * <p>
     * {@code Daily extra cost * total nights}
     */
    public BigDecimal getTotalGeneralExtrasCost() {
        long totalNights = dates.totalNights();
        return generalExtras.stream().reduce(
                BigDecimal.ZERO,
                (acc, next) -> acc.add(next.getTotalPrice(totalNights))
                , BigDecimal::add
        );
    }

    /**
     * Provided separately to allow break down to sub totals on invoices.
     *
     * @return Total cost of all guests meal plans
     */

    /**
     * Total cost including everything!
     * Provided separately to allow break down to sub totals on invoices.
     */
    public BigDecimal getTotalCost() {
        return getTotalRoomCostWithLateCheckoutFee()
                .add(getTotalGeneralExtrasCost());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(reservationId, that.reservationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservationId);
    }
}

