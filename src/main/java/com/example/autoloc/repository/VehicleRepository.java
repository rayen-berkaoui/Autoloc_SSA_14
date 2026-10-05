package com.example.autoloc.repository;

import com.example.autoloc.entities.Vehicle;
import com.example.autoloc.entities.enums.StatutVehicule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    boolean existsByImmatriculation(String immatriculation);

    List<Vehicle> findByStatut(StatutVehicule statut);

    List<Vehicle> findByAgenceIdAgence(Long idAgence);

    List<Vehicle> findByStatutAndAgenceIdAgence(StatutVehicule statut, Long idAgence);
}
