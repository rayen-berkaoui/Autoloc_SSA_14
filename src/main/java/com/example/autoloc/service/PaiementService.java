package com.example.autoloc.service;

import com.example.autoloc.dto.PaiementDto;
import com.example.autoloc.entities.Contrat;
import com.example.autoloc.entities.Paiement;
import com.example.autoloc.exception.ConflictException;
import com.example.autoloc.exception.ResourceNotFoundException;
import com.example.autoloc.repository.PaiementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PaiementService {

    private final PaiementRepository paiementRepository;
    private final ContratService contratService;

    @Transactional(readOnly = true)
    public List<PaiementDto.Response> findAll(Long idContrat) {
        List<Paiement> list = idContrat == null
                ? paiementRepository.findAll()
                : paiementRepository.findByContratIdContrat(idContrat);
        return list.stream().map(PaiementDto.Response::from).toList();
    }

    @Transactional(readOnly = true)
    public PaiementDto.Response findById(Long id) {
        return PaiementDto.Response.from(getEntity(id));
    }

    public PaiementDto.Response create(PaiementDto.Request r) {
        Contrat contrat = contratService.getEntity(r.idContrat());
        if (!contrat.isValide()) {
            throw new ConflictException("Le contrat n'est plus valide");
        }
        BigDecimal dejaPaye = contrat.getPaiements().stream()
                .map(Paiement::getMontant)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        if (dejaPaye.add(r.montant()).compareTo(contrat.getMontantTotal()) > 0) {
            throw new ConflictException("Le paiement dépasse le montant restant à payer ("
                    + contrat.getMontantTotal().subtract(dejaPaye) + ")");
        }
        Paiement p = new Paiement();
        p.setMontant(r.montant());
        p.setDatePaiement(r.datePaiement() != null ? r.datePaiement() : LocalDate.now());
        p.setModePaiement(r.modePaiement());
        p.setContrat(contrat);
        contrat.getPaiements().add(p);
        return PaiementDto.Response.from(paiementRepository.save(p));
    }

    public void delete(Long id) {
        Paiement p = getEntity(id);
        p.getContrat().getPaiements().remove(p);
    }

    private Paiement getEntity(Long id) {
        return paiementRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Paiement", id));
    }
}
