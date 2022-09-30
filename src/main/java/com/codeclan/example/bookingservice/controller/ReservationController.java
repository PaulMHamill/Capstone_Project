package com.codeclan.example.bookingservice.controller;

import com.codeclan.example.bookingservice.models.Reservation;
import com.codeclan.example.bookingservice.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReservationController {

    @Autowired
    ReservationRepository reservationRepository;

    @GetMapping("/reservations")
    public ResponseEntity<Reservation> postReservations(@RequestBody Reservation reservation) {
        reservationRepository.save(reservation);
        return new ResponseEntity<>(reservation, HttpStatus.CREATED);
    }

    @PutMapping(value="/reservations/{id}")
    public ResponseEntity<Reservation> putReservation(@RequestBody Reservation reservation, @PathVariable Long id) {
        Reservation reservationToUpdate = reservationRepository.findById(id).get();
        reservationToUpdate.setDateTo(reservation.getDateTo());
        reservationToUpdate.setDateFrom(reservation.getDateFrom());
        reservationToUpdate.setPod(reservation.getPod());
        reservationToUpdate.setGuest(reservation.getGuest());
        reservationToUpdate.setNumberOfNights(reservation.getNumberOfNights());
        reservationRepository.save(reservationToUpdate);
        return new ResponseEntity<>(reservationToUpdate, HttpStatus.OK);
    }

}
