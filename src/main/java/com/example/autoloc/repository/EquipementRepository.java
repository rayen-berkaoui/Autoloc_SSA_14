package com.example.autoloc.repository;

import com.example.autoloc.entities.Equipement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EquipementRepository extends JpaRepository<Equipement, Long> {

    List<Equipement> findByVehiculeIdVehicule(Long idVehicule);
}
