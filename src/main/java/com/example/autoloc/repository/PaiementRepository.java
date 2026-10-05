package com.example.autoloc.repository;

import com.example.autoloc.entities.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaiementRepository extends JpaRepository<Paiement, Long> {

    List<Paiement> findByContratIdContrat(Long idContrat);
}
