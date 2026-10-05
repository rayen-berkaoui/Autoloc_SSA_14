package com.example.autoloc.service;

import com.example.autoloc.dto.ReservationDto;
import com.example.autoloc.entities.Reservation;
import com.example.autoloc.entities.Vehicle;
import com.example.autoloc.entities.enums.StatutReservation;
import com.example.autoloc.exception.BusinessException;
import com.example.autoloc.exception.ConflictException;
import com.example.autoloc.exception.ResourceNotFoundException;
import com.example.autoloc.repository.MaintenanceRepository;
import com.example.autoloc.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationService {

    /** Reservations that block the vehicle for their period. */
    private static final Set<StatutReservation> ACTIVE =
            Set.of(StatutReservation.EN_ATTENTE, StatutReservation.CONFIRMEE);

    private final ReservationRepository reservationRepository;
    private final ClientService clientService;
    private final VehicleService vehicleService;
    private final VehicleStatusService statusService;
    private final MaintenanceRepository maintenanceRepository;

    @Transactional(readOnly = true)
    public List<ReservationDto.Response> findAll(Long idClient, Long idVehicule) {
        List<Reservation> list;
        if (idClient != null) {
            list = reservationRepository.findByClientIdClient(idClient);
            if (idVehicule != null) {
                list = list.stream().filter(r -> r.getVehicule().getIdVehicule().equals(idVehicule)).toList();
            }
        } else if (idVehicule != null) {
            list = reservationRepository.findByVehiculeIdVehicule(idVehicule);
        } else {
            list = reservationRepository.findAll();
        }
        return list.stream().map(ReservationDto.Response::from).toList();
    }

    @Transactional(readOnly = true)
    public ReservationDto.Response findById(Long id) {
        return ReservationDto.Response.from(getEntity(id));
    }

    public ReservationDto.Response create(ReservationDto.Request r) {
        Reservation res = new Reservation();
        res.setStatut(StatutReservation.EN_ATTENTE);
        apply(res, r, 0L);
        reservationRepository.save(res);
        statusService.refresh(res.getVehicule());
        return ReservationDto.Response.from(res);
    }

    public ReservationDto.Response update(Long id, ReservationDto.Request r) {
        Reservation res = getEntity(id);
        if (!ACTIVE.contains(res.getStatut())) {
            throw new ConflictException("Une réservation " + res.getStatut() + " ne peut plus être modifiée");
        }
        if (res.getContrat() != null) {
            throw new ConflictException("Cette réservation a déjà un contrat : modification impossible");
        }
        Vehicle ancien = res.getVehicule();
        apply(res, r, id);
        statusService.refresh(ancien);
        statusService.refresh(res.getVehicule());
        return ReservationDto.Response.from(res);
    }

    public ReservationDto.Response changeStatut(Long id, StatutReservation nouveau) {
        Reservation res = getEntity(id);
        if (!ACTIVE.contains(res.getStatut()) && res.getStatut() != nouveau) {
            throw new ConflictException("Une réservation " + res.getStatut() + " ne peut plus changer de statut");
        }
        res.setStatut(nouveau);
        statusService.refresh(res.getVehicule());
        return ReservationDto.Response.from(res);
    }

    public void delete(Long id) {
        Reservation res = getEntity(id);
        if (res.getContrat() != null) {
            throw new ConflictException("Cette réservation a un contrat : suppression impossible");
        }
        Vehicle vehicle = res.getVehicule();
        reservationRepository.delete(res);
        statusService.refresh(vehicle);
    }

    Reservation getEntity(Long id) {
        return reservationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reservation", id));
    }

    private void apply(Reservation res, ReservationDto.Request r, Long excludeId) {
        if (r.dateFin().isBefore(r.dateDebut())) {
            throw new BusinessException("dateFin ne peut pas être avant dateDebut");
        }
        boolean dateDebutModifiee = res.getDateDebut() == null || !res.getDateDebut().equals(r.dateDebut());
        if (dateDebutModifiee && r.dateDebut().isBefore(LocalDate.now())) {
            throw new BusinessException("dateDebut ne peut pas être dans le passé");
        }
        Vehicle vehicle = vehicleService.getEntity(r.idVehicule());
        if (maintenanceRepository.existsOverlap(vehicle.getIdVehicule(), r.dateDebut(), r.dateFin())) {
            throw new ConflictException("Le véhicule est en maintenance sur cette période");
        }
        if (reservationRepository.existsOverlap(vehicle.getIdVehicule(), r.dateDebut(), r.dateFin(), ACTIVE, excludeId)) {
            throw new ConflictException("Le véhicule est déjà réservé sur cette période");
        }
        res.setDateDebut(r.dateDebut());
        res.setDateFin(r.dateFin());
        res.setClient(clientService.getEntity(r.idClient()));
        res.setVehicule(vehicle);
    }
}
