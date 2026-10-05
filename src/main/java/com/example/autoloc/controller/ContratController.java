package com.example.autoloc.controller;

import com.example.autoloc.dto.ContratDto;
import com.example.autoloc.service.ContratService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contrats")
@RequiredArgsConstructor
public class ContratController {

    private final ContratService service;

    @GetMapping
    public List<ContratDto.Response> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ContratDto.Response findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContratDto.Response create(@Valid @RequestBody ContratDto.Request request) {
        return service.create(request);
    }

    @PatchMapping("/{id}/invalider")
    public ContratDto.Response invalider(@PathVariable Long id) {
        return service.invalider(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
