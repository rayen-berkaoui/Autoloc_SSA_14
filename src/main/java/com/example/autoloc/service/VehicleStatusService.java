package com.example.autoloc.service;

import com.example.autoloc.entities.Vehicle;
import com.example.autoloc.entities.enums.StatutReservation;
import com.example.autoloc.entities.enums.StatutVehicule;
import com.example.autoloc.repository.MaintenanceRepository;
import com.example.autoloc.repository.ReservationRepository;
import com.example.autoloc.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Set;

/**
 * Keeps Vehicle.statut in sync with reality. The status is derived, never set by hand:
 * EN_MAINTENANCE if a maintenance covers today, else LOUE if a CONFIRMEE reservation covers today,
 * else DISPONIBLE.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class VehicleStatusService {

    private final VehicleRepository vehicleRepository;
    private final MaintenanceRepository maintenanceRepository;
    private final ReservationRepository reservationRepository;

    /** Recomputes the status of one vehicle. Call it after any change to its maintenances or reservations. */
    public void refresh(Vehicle vehicle) {
        vehicle.setStatut(compute(vehicle.getIdVehicule(), LocalDate.now()));
    }

    /** Dates roll over without any request, so recompute everything at startup and every night. */
    @EventListener(ApplicationReadyEvent.class)
    @Scheduled(cron = "0 5 0 * * *")
    public void refreshAll() {
        LocalDate today = LocalDate.now();
        vehicleRepository.findAll().forEach(v -> v.setStatut(compute(v.getIdVehicule(), today)));
    }

    private StatutVehicule compute(Long idVehicule, LocalDate today) {
        if (maintenanceRepository.existsOverlap(idVehicule, today, today)) {
            return StatutVehicule.EN_MAINTENANCE;
        }
        if (reservationRepository.existsOverlap(idVehicule, today, today,
                Set.of(StatutReservation.CONFIRMEE), 0L)) {
            return StatutVehicule.LOUE;
        }
        return StatutVehicule.DISPONIBLE;
    }
}
