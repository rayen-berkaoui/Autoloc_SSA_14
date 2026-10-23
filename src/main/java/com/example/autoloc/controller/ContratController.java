package com.example.autoloc.controller;

import com.example.autoloc.entities.Contrat;
import com.example.autoloc.service.IContratService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contrats")
@RequiredArgsConstructor
public class ContratController {

    private final IContratService contratService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Contrat ajouterContrat(@RequestBody Contrat contrat) {
        return contratService.ajouterContrat(contrat);
    }

    @GetMapping
    public List<Contrat> consulterContrats() {
        return contratService.consulterContrats();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contrat> consulterContratById(@PathVariable Long id) {
        Contrat contrat = contratService.consulterContratById(id);
        return contrat == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(contrat);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contrat> modifierContrat(@PathVariable Long id, @RequestBody Contrat contrat) {
        if (contratService.consulterContratById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        contrat.setIdContrat(id);
        return ResponseEntity.ok(contratService.modifierContrat(contrat));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerContratById(@PathVariable Long id) {
        if (contratService.consulterContratById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        contratService.supprimerContratById(id);
        return ResponseEntity.noContent().build();
    }
}
