package models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "reservation")
public class Reservation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "GUEST_ID")
    private Guest guest;

    @OneToOne
    @JoinColumn(name = "POD_ID")
    private Pod pod_id;

    @Column(name = "numberOfNights")
    private int numberOfNights;

    @Column(name = "pod")
    private Pod pod;

    public Reservation(int numberOfNights, Pod pod) {
        this.numberOfNights = numberOfNights;
        this.pod = pod;
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

    public Pod getPod_id() {
        return pod_id;
    }

    public void setPod_id(Pod pod_id) {
        this.pod_id = pod_id;
    }

    public double getTotalForStay() {
        return this.numberOfNights * this.pod.getNightlyRate();
    }

    public Reservation createReservation(Pod pod, int numberOfNights) {
        Reservation reservation = new Reservation(numberOfNights, pod);
        return reservation;
    }
}
