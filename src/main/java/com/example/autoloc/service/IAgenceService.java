package com.example.autoloc.service;

import com.example.autoloc.entities.Agence;

import java.util.List;

public interface IAgenceService {
    Agence ajouterAgence(Agence agence);
    Agence modifierAgence(Agence agence);
    List<Agence> consulterAgences();
    Agence consulterAgenceById(Long idAgence);
    void supprimerAgenceById(Long idAgence);
}
