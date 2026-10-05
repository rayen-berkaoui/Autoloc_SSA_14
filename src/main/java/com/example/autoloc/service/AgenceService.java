package com.example.autoloc.service;

import com.example.autoloc.dto.AgenceDto;
import com.example.autoloc.entities.Agence;
import com.example.autoloc.exception.ResourceNotFoundException;
import com.example.autoloc.repository.AgenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AgenceService {

    private final AgenceRepository agenceRepository;

    @Transactional(readOnly = true)
    public List<AgenceDto.Response> findAll() {
        return agenceRepository.findAll().stream().map(AgenceDto.Response::from).toList();
    }

    @Transactional(readOnly = true)
    public AgenceDto.Response findById(Long id) {
        return AgenceDto.Response.from(getEntity(id));
    }

    public AgenceDto.Response create(AgenceDto.Request r) {
        Agence a = new Agence();
        apply(a, r);
        return AgenceDto.Response.from(agenceRepository.save(a));
    }

    public AgenceDto.Response update(Long id, AgenceDto.Request r) {
        Agence a = getEntity(id);
        apply(a, r);
        return AgenceDto.Response.from(a);
    }

    public void delete(Long id) {
        agenceRepository.delete(getEntity(id));
    }

    /** Used by other services that need the entity itself. */
    Agence getEntity(Long id) {
        return agenceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Agence", id));
    }

    private void apply(Agence a, AgenceDto.Request r) {
        a.setNom(r.nom());
        a.setVille(r.ville());
        a.setAdresse(r.adresse());
        a.setTelephone(r.telephone());
    }
}
