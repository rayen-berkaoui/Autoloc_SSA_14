package com.example.autoloc.controller;

import com.example.autoloc.entities.Paiement;
import com.example.autoloc.service.IPaiementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/paiements")
@RequiredArgsConstructor
public class PaiementController {

    private final IPaiementService paiementService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Paiement ajouterPaiement(@RequestBody Paiement paiement) {
        return paiementService.ajouterPaiement(paiement);
    }

    @GetMapping
    public List<Paiement> consulterPaiements() {
        return paiementService.consulterPaiements();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paiement> consulterPaiementById(@PathVariable Long id) {
        Paiement paiement = paiementService.consulterPaiementById(id);
        return paiement == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(paiement);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Paiement> modifierPaiement(@PathVariable Long id, @RequestBody Paiement paiement) {
        if (paiementService.consulterPaiementById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        paiement.setIdPaiement(id);
        return ResponseEntity.ok(paiementService.modifierPaiement(paiement));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerPaiementById(@PathVariable Long id) {
        if (paiementService.consulterPaiementById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        paiementService.supprimerPaiementById(id);
        return ResponseEntity.noContent().build();
    }
}
