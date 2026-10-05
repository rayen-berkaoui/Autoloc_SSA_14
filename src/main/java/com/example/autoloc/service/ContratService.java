package com.example.autoloc.service;

import com.example.autoloc.dto.ContratDto;
import com.example.autoloc.entities.Contrat;
import com.example.autoloc.entities.Reservation;
import com.example.autoloc.entities.enums.StatutReservation;
import com.example.autoloc.exception.BusinessException;
import com.example.autoloc.exception.ConflictException;
import com.example.autoloc.exception.ResourceNotFoundException;
import com.example.autoloc.repository.ContratRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ContratService {

    private final ContratRepository contratRepository;
    private final ReservationService reservationService;

    @Transactional(readOnly = true)
    public List<ContratDto.Response> findAll() {
        return contratRepository.findAll().stream().map(ContratDto.Response::from).toList();
    }

    @Transactional(readOnly = true)
    public ContratDto.Response findById(Long id) {
        return ContratDto.Response.from(getEntity(id));
    }

    /** Creates the contract of a CONFIRMEE reservation; the amount is days (inclusive) x daily rate. */
    public ContratDto.Response create(ContratDto.Request r) {
        Reservation res = reservationService.getEntity(r.idReservation());
        if (res.getStatut() != StatutReservation.CONFIRMEE) {
            throw new BusinessException("La réservation doit être CONFIRMEE pour créer un contrat");
        }
        if (contratRepository.existsByReservationIdReservation(res.getIdReservation())) {
            throw new ConflictException("Cette réservation a déjà un contrat");
        }
        long jours = ChronoUnit.DAYS.between(res.getDateDebut(), res.getDateFin()) + 1;
        BigDecimal montant = res.getVehicule().getTarifJournalier().multiply(BigDecimal.valueOf(jours));

        Contrat c = new Contrat();
        c.setReservation(res);
        c.setDateSignature(LocalDate.now());
        c.setMontantTotal(montant);
        c.setValide(true);
        return ContratDto.Response.from(contratRepository.save(c));
    }

    public ContratDto.Response invalider(Long id) {
        Contrat c = getEntity(id);
        c.setValide(false);
        return ContratDto.Response.from(c);
    }

    public void delete(Long id) {
        Contrat c = getEntity(id);
        if (!c.getPaiements().isEmpty()) {
            throw new ConflictException("Ce contrat a des paiements : suppression impossible");
        }
        contratRepository.delete(c);
    }

    Contrat getEntity(Long id) {
        return contratRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Contrat", id));
    }
}
