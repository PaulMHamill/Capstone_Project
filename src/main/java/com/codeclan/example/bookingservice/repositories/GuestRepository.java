package com.codeclan.example.bookingservice.repositories;

import com.codeclan.example.bookingservice.models.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {
}
