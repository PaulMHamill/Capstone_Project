package models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "reservation")
public class Reservation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

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

    public double getTotalForStay() {
        return this.numberOfNights * this.pod.getNightlyRate();
    }

    public Reservation createReservation(Pod pod, int numberOfNights) {
        Reservation reservation = new Reservation(numberOfNights, pod);
        return reservation;
    }
}
