package com.codeclan.example.bookingservice.controller;

import com.codeclan.example.bookingservice.models.Pod;
import com.codeclan.example.bookingservice.repositories.PodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PodController {
    @Autowired
    PodRepository podRepository;

    @GetMapping(value = "/pods")
    public ResponseEntity<List<Pod>> getAllPods(){
        return new ResponseEntity<>(podRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/pods/{id}")
    public ResponseEntity getShip(@PathVariable Long id){
        return new ResponseEntity<>(podRepository.findById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/pods")
    public ResponseEntity<Pod> createShip(@RequestBody Pod pod){
        podRepository.save(pod);
        return new ResponseEntity<>(pod, HttpStatus.CREATED);
    }

    @PutMapping(value="/pods/{id}")
    public ResponseEntity<Pod> putPod(@RequestBody Pod pod, @PathVariable Long id){
        Pod podToUpdate = podRepository.findById(id).get();
        podToUpdate.setPodName(pod.getPodName());
        podToUpdate.setReservations(pod.getReservations());
        podToUpdate.setType(pod.getType());
        podToUpdate.setNightlyRate(pod.getNightlyRate());
        podRepository.save(podToUpdate);
        return new ResponseEntity<>(podToUpdate, HttpStatus.OK);
    }

    @DeleteMapping(value="/pods/{id}")
    public ResponseEntity<Long> deletePod(@PathVariable Long id){
        podRepository.deleteById(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

}
