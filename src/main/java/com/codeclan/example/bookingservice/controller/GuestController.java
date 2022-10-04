package com.codeclan.example.bookingservice.controller;

import com.codeclan.example.bookingservice.models.Guest;
import com.codeclan.example.bookingservice.models.Reservation;
import com.codeclan.example.bookingservice.repositories.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class GuestController {
        @Autowired
        GuestRepository guestRepository;


        @GetMapping(value = "/guests")
        public ResponseEntity<List<Guest>> getAllGuests(){
            return new ResponseEntity<>(guestRepository.findAll(), HttpStatus.OK);
        }

        @GetMapping(value = "/guests/{id}")
        public ResponseEntity getGuest(@PathVariable Long id){
            return new ResponseEntity<>(guestRepository.findById(id), HttpStatus.OK);
        }

        @PostMapping(value = "/guests")
        public ResponseEntity<Guest> postGuest(@RequestBody Guest guest){
            guestRepository.save(guest);
            return new ResponseEntity<>(guest, HttpStatus.CREATED);
        }

        @PutMapping(value="/guests/{id}")
        public ResponseEntity<Guest> putGuest(@RequestBody Guest guest, @PathVariable Long id){
            Guest foundGuest = guestRepository.findById(id).get();
            foundGuest.setFirstName(guest.getFirstName());
            foundGuest.setSecondName(guest.getSecondName());
            foundGuest.setEmail(guest.getEmail());
            foundGuest.setReservations(guest.getReservations());
            guestRepository.save(foundGuest);
            return new ResponseEntity<>(foundGuest, HttpStatus.OK);
        }

        @DeleteMapping(value="/guests/{id}")
        public ResponseEntity<Long> deleteGuest(@PathVariable Long id){
            guestRepository.deleteById(id);
            return new ResponseEntity<>(id, HttpStatus.OK);
        }

//        @GetMapping(value = "/guests/{guestId}/reservations")
//        public ResponseEntity<List<Reservation>> getReservationsForGuest(@PathVariable Long guestId) {
//            return new ResponseEntity<>(reservationRepository.findAllByReservationsGuestId(guestId), HttpStatus.OK);
//        }

    }

