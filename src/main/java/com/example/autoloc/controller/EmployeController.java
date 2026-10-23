package com.example.autoloc.controller;

import com.example.autoloc.entities.Employe;
import com.example.autoloc.service.IEmployeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employes")
@RequiredArgsConstructor
public class EmployeController {

    private final IEmployeService employeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employe ajouterEmploye(@RequestBody Employe employe) {
        return employeService.ajouterEmploye(employe);
    }

    @GetMapping
    public List<Employe> consulterEmployes() {
        return employeService.consulterEmployes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employe> consulterEmployeById(@PathVariable Long id) {
        Employe employe = employeService.consulterEmployeById(id);
        return employe == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(employe);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employe> modifierEmploye(@PathVariable Long id, @RequestBody Employe employe) {
        if (employeService.consulterEmployeById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        employe.setIdEmploye(id);
        return ResponseEntity.ok(employeService.modifierEmploye(employe));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerEmployeById(@PathVariable Long id) {
        if (employeService.consulterEmployeById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        employeService.supprimerEmployeById(id);
        return ResponseEntity.noContent().build();
    }
}
