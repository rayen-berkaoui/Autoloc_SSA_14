package com.example.autoloc.service;

import com.example.autoloc.entities.Contrat;
import com.example.autoloc.repository.ContratRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class IContratServiceImp implements IContratService {
    /*@Autowired
        private ContratRepository contratRepository;*/
    private final ContratRepository contratRepository;

    @Override
    public Contrat ajouterContrat(Contrat contrat) {
        return contratRepository.save(contrat);
    }

    @Override
    public Contrat modifierContrat(Contrat contrat) {
        contratRepository.findById(contrat.getIdContrat())
                .ifPresent(existing -> contrat.setPaiements(existing.getPaiements()));
        return contratRepository.save(contrat);
    }

    @Override
    public List<Contrat> consulterContrats() {
        return contratRepository.findAll();
    }

    @Override
    public Contrat consulterContratById(Long idContrat) {
        return contratRepository.findById(idContrat).orElse(null);
    }

    @Override
    public void supprimerContratById(Long idContrat) {
         contratRepository.deleteById(idContrat);

    }

}
