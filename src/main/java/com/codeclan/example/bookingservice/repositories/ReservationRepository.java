package com.codeclan.example.bookingservice.repositories;

import com.codeclan.example.bookingservice.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
