package com.example.autoloc.controller;

import com.example.autoloc.dto.ReservationDto;
import com.example.autoloc.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService service;

    @GetMapping
    public List<ReservationDto.Response> findAll(@RequestParam(required = false) Long idClient,
                                                 @RequestParam(required = false) Long idVehicule) {
        return service.findAll(idClient, idVehicule);
    }

    @GetMapping("/{id}")
    public ReservationDto.Response findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationDto.Response create(@Valid @RequestBody ReservationDto.Request request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public ReservationDto.Response update(@PathVariable Long id, @Valid @RequestBody ReservationDto.Request request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/statut")
    public ReservationDto.Response changeStatut(@PathVariable Long id,
                                                @Valid @RequestBody ReservationDto.StatutRequest request) {
        return service.changeStatut(id, request.statut());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
