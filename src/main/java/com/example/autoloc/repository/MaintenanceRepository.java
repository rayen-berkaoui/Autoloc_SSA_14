package com.example.autoloc.repository;

import com.example.autoloc.entities.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {

    List<Maintenance> findByVehiculeIdVehicule(Long idVehicule);

    /** True if the vehicle has a maintenance overlapping [debut, fin] (a null dateFin means still ongoing). */
    @Query("""
            select count(m) > 0 from Maintenance m
            where m.vehicule.idVehicule = :idVehicule
              and m.dateDebut <= :fin
              and (m.dateFin is null or m.dateFin >= :debut)
            """)
    boolean existsOverlap(@Param("idVehicule") Long idVehicule,
                          @Param("debut") LocalDate debut,
                          @Param("fin") LocalDate fin);
}
