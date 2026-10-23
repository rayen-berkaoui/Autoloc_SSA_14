package com.example.autoloc.controller;

import com.example.autoloc.entities.Equipement;
import com.example.autoloc.service.IEquipementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipements")
@RequiredArgsConstructor
public class EquipementController {

    private final IEquipementService equipementService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Equipement ajouterEquipement(@RequestBody Equipement equipement) {
        return equipementService.ajouterEquipement(equipement);
    }

    @GetMapping
    public List<Equipement> consulterEquipements() {
        return equipementService.consulterEquipements();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipement> consulterEquipementById(@PathVariable Long id) {
        Equipement equipement = equipementService.consulterEquipementById(id);
        return equipement == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(equipement);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Equipement> modifierEquipement(@PathVariable Long id, @RequestBody Equipement equipement) {
        if (equipementService.consulterEquipementById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        equipement.setIdEquipement(id);
        return ResponseEntity.ok(equipementService.modifierEquipement(equipement));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerEquipementById(@PathVariable Long id) {
        if (equipementService.consulterEquipementById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        equipementService.supprimerEquipementById(id);
        return ResponseEntity.noContent().build();
    }
}
