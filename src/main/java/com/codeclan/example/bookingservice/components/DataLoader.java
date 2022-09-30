package com.codeclan.example.bookingservice.components;

import com.codeclan.example.bookingservice.models.Guest;
import com.codeclan.example.bookingservice.models.Pod;
import com.codeclan.example.bookingservice.models.PodType;
import com.codeclan.example.bookingservice.models.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import com.codeclan.example.bookingservice.repositories.GuestRepository;
import com.codeclan.example.bookingservice.repositories.PodRepository;
import com.codeclan.example.bookingservice.repositories.ReservationRepository;

import java.text.ParseException;

//@Profile("!test")
@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    GuestRepository guestRepository;
    @Autowired
    PodRepository podRepository;
    @Autowired
    ReservationRepository reservationRepository;


    public DataLoader() {
    }

    public void run(ApplicationArguments args) throws ParseException {

        System.out.println("DATA LOADER");

        Guest guest1 = new Guest("Paul", "Hamill", "PH@gmail.com");
        guestRepository.save(guest1);

        Guest guest2 = new Guest("Cheryl", "Stewart", "CS@gmail.com");
        guestRepository.save(guest2);

        Guest guest3 = new Guest("John", "Smith", "JS@gmail.com");
        guestRepository.save(guest3);

        Guest guest4 = new Guest("Mary", "Smith", "MS@gmail.com");
        guestRepository.save(guest4);

        Pod pod1 = new Pod("Bute", PodType.DOUBLE, 400.00);
        podRepository.save(pod1);

        Pod pod2 = new Pod("Skye", PodType.DOUBLE, 400.00);
        podRepository.save(pod2);

        Pod pod3 = new Pod("Islay", PodType.DOUBLE, 400.00);
        podRepository.save(pod3);

        Pod pod4 = new Pod("Jura", PodType.DOUBLE, 400.00);
        podRepository.save(pod4);

        Pod pod5 = new Pod("Arran", PodType.DOUBLE, 400.00);
        podRepository.save(pod5);

        Pod pod6 = new Pod("Harris", PodType.DOUBLE, 400.00);
        podRepository.save(pod6);

        Reservation reservation1 = new Reservation(2, pod1, guest1);
        reservationRepository.save(reservation1);

        Reservation reservation2 = new Reservation(3, pod3, guest3 );
        reservationRepository.save(reservation2);
        
    }
}
