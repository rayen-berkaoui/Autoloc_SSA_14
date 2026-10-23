package com.example.autoloc.controller;

import com.example.autoloc.entities.Maintenance;
import com.example.autoloc.service.IMaintenanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/maintenances")
@RequiredArgsConstructor
public class MaintenanceController {

    private final IMaintenanceService maintenanceService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Maintenance ajouterMaintenance(@RequestBody Maintenance maintenance) {
        return maintenanceService.ajouterMaintenance(maintenance);
    }

    @GetMapping
    public List<Maintenance> consulterMaintenances() {
        return maintenanceService.consulterMaintenances();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Maintenance> consulterMaintenanceById(@PathVariable Long id) {
        Maintenance maintenance = maintenanceService.consulterMaintenanceById(id);
        return maintenance == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(maintenance);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Maintenance> modifierMaintenance(@PathVariable Long id, @RequestBody Maintenance maintenance) {
        if (maintenanceService.consulterMaintenanceById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        maintenance.setIdMaintenance(id);
        return ResponseEntity.ok(maintenanceService.modifierMaintenance(maintenance));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerMaintenanceById(@PathVariable Long id) {
        if (maintenanceService.consulterMaintenanceById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        maintenanceService.supprimerMaintenanceById(id);
        return ResponseEntity.noContent().build();
    }
}
