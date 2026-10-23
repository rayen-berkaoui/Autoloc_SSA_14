package com.example.autoloc.service;

import com.example.autoloc.entities.Reservation;
import com.example.autoloc.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class IReservationServiceImp implements IReservationService {
    private final ReservationRepository reservationRepository;

    @Override
    public Reservation ajouterReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation modifierReservation(Reservation reservation) {
        reservationRepository.findById(reservation.getIdReservation())
                .ifPresent(existing -> reservation.setContrat(existing.getContrat()));
        return reservationRepository.save(reservation);
    }

    @Override
    public List<Reservation> consulterReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation consulterReservationById(Long idReservation) {
        return reservationRepository.findById(idReservation).orElse(null);
    }

    @Override
    public void supprimerReservationById(Long idReservation) {
        reservationRepository.deleteById(idReservation);
    }
}
