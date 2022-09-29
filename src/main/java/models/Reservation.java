package models;

public class Reservation {

    private int numberOfNights;
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
        Reservation reservation = new Reservation(numberOfNights, Pod);
        return reservation;
    }
}
