package com.codeclan.example.bookingservice.repositories;

import com.codeclan.example.bookingservice.models.Pod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PodRepository extends JpaRepository<Pod, Long> {
}
