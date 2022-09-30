package com.codeclan.example.bookingservice.models;

import java.util.ArrayList;

public abstract class Unit {

    private int capacity;
    private ArrayList<Guest> guests;

    public Unit(int capacity) {
        this.capacity = capacity;
        this.guests = new ArrayList<Guest>();
    }

    public Unit(){
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public ArrayList<Guest> getGuests() {
        return guests;
    }

    public void setGuests(ArrayList<Guest> guests) {
        this.guests = guests;
    }

    public int guestListSize(){
        return this.guests.size();
    }

    public void checkInGuest(Guest guest) {
        if (this.guestListSize()< this.capacity){
            this.guests.add(guest);
        }
    }

    public void checkOutGuests() {
        if (this.isOccupied()) {
            this.guests.clear();
        }
    }

    public boolean isOccupied() {
        return this.guestListSize() > 0;
    }

    public boolean isVacant() {
        return this.guestListSize() == 0;
    }
}
