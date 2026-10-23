package com.example.autoloc.controller;

import com.example.autoloc.entities.Agence;
import com.example.autoloc.service.IAgenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agences")
@RequiredArgsConstructor
public class AgenceController {

    private final IAgenceService agenceService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Agence ajouterAgence(@RequestBody Agence agence) {
        return agenceService.ajouterAgence(agence);
    }

    @GetMapping
    public List<Agence> consulterAgences() {
        return agenceService.consulterAgences();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Agence> consulterAgenceById(@PathVariable Long id) {
        Agence agence = agenceService.consulterAgenceById(id);
        return agence == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(agence);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Agence> modifierAgence(@PathVariable Long id, @RequestBody Agence agence) {
        if (agenceService.consulterAgenceById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        agence.setIdAgence(id);
        return ResponseEntity.ok(agenceService.modifierAgence(agence));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerAgenceById(@PathVariable Long id) {
        if (agenceService.consulterAgenceById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        agenceService.supprimerAgenceById(id);
        return ResponseEntity.noContent().build();
    }
}
