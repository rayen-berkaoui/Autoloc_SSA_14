package com.example.autoloc.service;

import com.example.autoloc.entities.Vehicle;
import com.example.autoloc.repository.VehicleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class IVehicleServiceImp implements IVehicleService {
    private final VehicleRepository vehicleRepository;

    @Override
    public Vehicle ajouterVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    @Override
    public Vehicle modifierVehicle(Vehicle vehicle) {
        // les listes ne sont pas désérialisées : on conserve celles en base (orphanRemoval)
        vehicleRepository.findById(vehicle.getIdVehicule()).ifPresent(existing -> {
            vehicle.setMaintenances(existing.getMaintenances());
            vehicle.setEquipements(existing.getEquipements());
            vehicle.setReservations(existing.getReservations());
        });
        return vehicleRepository.save(vehicle);
    }

    @Override
    public List<Vehicle> consulterVehicles() {
        return vehicleRepository.findAll();
    }

    @Override
    public Vehicle consulterVehicleById(Long idVehicle) {
        return vehicleRepository.findById(idVehicle).orElse(null);
    }

    @Override
    public void supprimerVehicleById(Long idVehicle) {
        vehicleRepository.deleteById(idVehicle);
    }
}
