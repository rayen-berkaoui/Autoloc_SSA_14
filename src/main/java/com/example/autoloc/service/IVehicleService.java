package com.example.autoloc.service;

import com.example.autoloc.entities.Vehicle;

import java.util.List;

public interface IVehicleService {
    Vehicle ajouterVehicle(Vehicle vehicle);
    Vehicle modifierVehicle(Vehicle vehicle);
    List<Vehicle> consulterVehicles();
    Vehicle consulterVehicleById(Long idVehicle);
    void supprimerVehicleById(Long idVehicle);
}
