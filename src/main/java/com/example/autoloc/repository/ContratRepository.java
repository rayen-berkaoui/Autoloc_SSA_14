package com.example.autoloc.repository;

import com.example.autoloc.entities.Contrat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContratRepository extends JpaRepository<Contrat, Long> {

    boolean existsByReservationIdReservation(Long idReservation);
}
