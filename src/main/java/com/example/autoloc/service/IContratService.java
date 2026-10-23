package com.example.autoloc.service;

import com.example.autoloc.entities.Contrat;

import java.util.List;

public interface IContratService {
    Contrat ajouterContrat(Contrat contrat);
    Contrat modifierContrat(Contrat contrat);
    List<Contrat>consulterContrats();
    Contrat consulterContratById(Long idContrat);
    void supprimerContratById(Long idContrat);
}
