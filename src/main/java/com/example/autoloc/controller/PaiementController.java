package com.example.autoloc.controller;

import com.example.autoloc.dto.PaiementDto;
import com.example.autoloc.service.PaiementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/paiements")
@RequiredArgsConstructor
public class PaiementController {

    private final PaiementService service;

    @GetMapping
    public List<PaiementDto.Response> findAll(@RequestParam(required = false) Long idContrat) {
        return service.findAll(idContrat);
    }

    @GetMapping("/{id}")
    public PaiementDto.Response findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaiementDto.Response create(@Valid @RequestBody PaiementDto.Request request) {
        return service.create(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
