package com.example.autoloc.controller;

import com.example.autoloc.entities.Vehicle;
import com.example.autoloc.service.IVehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final IVehicleService vehicleService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Vehicle ajouterVehicle(@RequestBody Vehicle vehicle) {
        return vehicleService.ajouterVehicle(vehicle);
    }

    @GetMapping
    public List<Vehicle> consulterVehicles() {
        return vehicleService.consulterVehicles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> consulterVehicleById(@PathVariable Long id) {
        Vehicle vehicle = vehicleService.consulterVehicleById(id);
        return vehicle == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(vehicle);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vehicle> modifierVehicle(@PathVariable Long id, @RequestBody Vehicle vehicle) {
        if (vehicleService.consulterVehicleById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        vehicle.setIdVehicule(id);
        return ResponseEntity.ok(vehicleService.modifierVehicle(vehicle));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerVehicleById(@PathVariable Long id) {
        if (vehicleService.consulterVehicleById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        vehicleService.supprimerVehicleById(id);
        return ResponseEntity.noContent().build();
    }
}
