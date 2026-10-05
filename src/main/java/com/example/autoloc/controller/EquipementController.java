package com.example.autoloc.controller;

import com.example.autoloc.dto.EquipementDto;
import com.example.autoloc.service.EquipementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipements")
@RequiredArgsConstructor
public class EquipementController {

    private final EquipementService service;

    @GetMapping
    public List<EquipementDto.Response> findAll(@RequestParam(required = false) Long idVehicule) {
        return service.findAll(idVehicule);
    }

    @GetMapping("/{id}")
    public EquipementDto.Response findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EquipementDto.Response create(@Valid @RequestBody EquipementDto.Request request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public EquipementDto.Response update(@PathVariable Long id, @Valid @RequestBody EquipementDto.Request request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
