package com.example.autoloc.service;

import com.example.autoloc.entities.Maintenance;

import java.util.List;

public interface IMaintenanceService {
    Maintenance ajouterMaintenance(Maintenance maintenance);
    Maintenance modifierMaintenance(Maintenance maintenance);
    List<Maintenance> consulterMaintenances();
    Maintenance consulterMaintenanceById(Long idMaintenance);
    void supprimerMaintenanceById(Long idMaintenance);
}
