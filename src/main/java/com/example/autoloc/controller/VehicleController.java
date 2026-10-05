package com.example.autoloc.controller;

import com.example.autoloc.dto.VehicleDto;
import com.example.autoloc.entities.enums.StatutVehicule;
import com.example.autoloc.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicules")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService service;

    @GetMapping
    public List<VehicleDto.Response> findAll(@RequestParam(required = false) StatutVehicule statut,
                                             @RequestParam(required = false) Long idAgence) {
        return service.findAll(statut, idAgence);
    }

    @GetMapping("/{id}")
    public VehicleDto.Response findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VehicleDto.Response create(@Valid @RequestBody VehicleDto.Request request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public VehicleDto.Response update(@PathVariable Long id, @Valid @RequestBody VehicleDto.Request request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
