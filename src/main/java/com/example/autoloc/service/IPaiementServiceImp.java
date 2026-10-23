package com.example.autoloc.service;

import com.example.autoloc.entities.Paiement;
import com.example.autoloc.repository.PaiementRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class IPaiementServiceImp implements IPaiementService {
    private final PaiementRepository paiementRepository;

    @Override
    public Paiement ajouterPaiement(Paiement paiement) {
        return paiementRepository.save(paiement);
    }

    @Override
    public Paiement modifierPaiement(Paiement paiement) {
        return paiementRepository.save(paiement);
    }

    @Override
    public List<Paiement> consulterPaiements() {
        return paiementRepository.findAll();
    }

    @Override
    public Paiement consulterPaiementById(Long idPaiement) {
        return paiementRepository.findById(idPaiement).orElse(null);
    }

    @Override
    public void supprimerPaiementById(Long idPaiement) {
        paiementRepository.deleteById(idPaiement);
    }
}
