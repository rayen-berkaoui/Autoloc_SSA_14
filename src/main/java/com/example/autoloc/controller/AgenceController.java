package com.example.autoloc.controller;

import com.example.autoloc.dto.AgenceDto;
import com.example.autoloc.service.AgenceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agences")
@RequiredArgsConstructor
public class AgenceController {

    private final AgenceService service;

    @GetMapping
    public List<AgenceDto.Response> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public AgenceDto.Response findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AgenceDto.Response create(@Valid @RequestBody AgenceDto.Request request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public AgenceDto.Response update(@PathVariable Long id, @Valid @RequestBody AgenceDto.Request request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
