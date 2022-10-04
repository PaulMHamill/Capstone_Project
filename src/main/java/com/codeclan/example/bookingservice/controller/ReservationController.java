package com.codeclan.example.bookingservice.controller;

import com.codeclan.example.bookingservice.models.Reservation;
import com.codeclan.example.bookingservice.repositories.GuestRepository;
import com.codeclan.example.bookingservice.repositories.PodRepository;
import com.codeclan.example.bookingservice.repositories.ReservationRepository;
import com.codeclan.example.bookingservice.reservationprocess.ReservationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class ReservationController {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    PodRepository podRepository;

    @Autowired
    GuestRepository guestRepository;

//    private TimeProvider timeProvider;

    @GetMapping("/reservations")
    public ResponseEntity <List<Reservation>> getReservations() {
        return new ResponseEntity<>(reservationRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/reservations/{id}")
    public ResponseEntity getReservation(@PathVariable Long id){
        return new ResponseEntity<>(reservationRepository.findById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation){
        reservationRepository.save(reservation);
        return new ResponseEntity<>(reservation, HttpStatus.CREATED);
    }

    @DeleteMapping(value="/reservations/{id}")
    public ResponseEntity<Long> deleteReservation(@PathVariable Long id){
        reservationRepository.deleteById(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PutMapping(value="/reservations/{id}")
    public ResponseEntity<Reservation> putReservation(@RequestBody Reservation reservation, @PathVariable Long id){
        Reservation reservationToUpdate = reservationRepository.findById(id).get();
        reservationToUpdate.setCheckInDate(reservation.getCheckInDate());
        reservationToUpdate.setCheckOutDate(reservation.getCheckOutDate());
        reservationToUpdate.setGuest(reservation.getGuest());
        reservationRepository.save(reservationToUpdate);
        return new ResponseEntity<>(reservationToUpdate, HttpStatus.OK);
    }

    @ModelAttribute("reservationProcess")
    public ReservationForm getReservationForm() {
        return new ReservationForm();
    }

//     Form step 0
//
//      Entry point to begin the reservation process. Dates

    // Form step 1 - Guest


  //  Form step 2 - review reservation

//    Form step 3 - payment

    // End form

    @GetMapping("/reservation/completed")
    public String getFormCompleted() {
        return "reservation/completed";
//    }


    }}


