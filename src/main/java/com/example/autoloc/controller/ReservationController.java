package com.example.autoloc.controller;

import com.example.autoloc.entities.Reservation;
import com.example.autoloc.service.IReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final IReservationService reservationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Reservation ajouterReservation(@RequestBody Reservation reservation) {
        return reservationService.ajouterReservation(reservation);
    }

    @GetMapping
    public List<Reservation> consulterReservations() {
        return reservationService.consulterReservations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> consulterReservationById(@PathVariable Long id) {
        Reservation reservation = reservationService.consulterReservationById(id);
        return reservation == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(reservation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reservation> modifierReservation(@PathVariable Long id, @RequestBody Reservation reservation) {
        if (reservationService.consulterReservationById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        reservation.setIdReservation(id);
        return ResponseEntity.ok(reservationService.modifierReservation(reservation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerReservationById(@PathVariable Long id) {
        if (reservationService.consulterReservationById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        reservationService.supprimerReservationById(id);
        return ResponseEntity.noContent().build();
    }
}
