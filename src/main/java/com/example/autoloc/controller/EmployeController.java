package com.example.autoloc.controller;

import com.example.autoloc.dto.EmployeDto;
import com.example.autoloc.service.EmployeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employes")
@RequiredArgsConstructor
public class EmployeController {

    private final EmployeService service;

    @GetMapping
    public List<EmployeDto.Response> findAll(@RequestParam(required = false) Long idAgence) {
        return service.findAll(idAgence);
    }

    @GetMapping("/{id}")
    public EmployeDto.Response findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeDto.Response create(@Valid @RequestBody EmployeDto.Request request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public EmployeDto.Response update(@PathVariable Long id, @Valid @RequestBody EmployeDto.Request request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
