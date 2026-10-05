package com.example.autoloc.repository;

import com.example.autoloc.entities.Reservation;
import com.example.autoloc.entities.enums.StatutReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByClientIdClient(Long idClient);

    List<Reservation> findByVehiculeIdVehicule(Long idVehicule);

    /** True if the vehicle already has an active reservation overlapping [debut, fin]. */
    @Query("""
            select count(r) > 0 from Reservation r
            where r.vehicule.idVehicule = :idVehicule
              and r.statut in :statuts
              and r.dateDebut <= :fin
              and r.dateFin >= :debut
              and r.idReservation <> :excludeId
            """)
    boolean existsOverlap(@Param("idVehicule") Long idVehicule,
                          @Param("debut") LocalDate debut,
                          @Param("fin") LocalDate fin,
                          @Param("statuts") Collection<StatutReservation> statuts,
                          @Param("excludeId") Long excludeId);
}
