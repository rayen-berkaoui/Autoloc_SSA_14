package com.example.autoloc.controller;

import com.example.autoloc.dto.MaintenanceDto;
import com.example.autoloc.service.MaintenanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/maintenances")
@RequiredArgsConstructor
public class MaintenanceController {

    private final MaintenanceService service;

    @GetMapping
    public List<MaintenanceDto.Response> findAll(@RequestParam(required = false) Long idVehicule) {
        return service.findAll(idVehicule);
    }

    @GetMapping("/{id}")
    public MaintenanceDto.Response findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MaintenanceDto.Response create(@Valid @RequestBody MaintenanceDto.Request request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public MaintenanceDto.Response update(@PathVariable Long id, @Valid @RequestBody MaintenanceDto.Request request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
