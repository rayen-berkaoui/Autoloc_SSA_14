package com.example.autoloc.service;

import com.example.autoloc.dto.EmployeDto;
import com.example.autoloc.entities.Employe;
import com.example.autoloc.exception.ResourceNotFoundException;
import com.example.autoloc.repository.EmployeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeService {

    private final EmployeRepository employeRepository;
    private final AgenceService agenceService;

    @Transactional(readOnly = true)
    public List<EmployeDto.Response> findAll(Long idAgence) {
        List<Employe> employes = idAgence == null
                ? employeRepository.findAll()
                : employeRepository.findByAgenceIdAgence(idAgence);
        return employes.stream().map(EmployeDto.Response::from).toList();
    }

    @Transactional(readOnly = true)
    public EmployeDto.Response findById(Long id) {
        return EmployeDto.Response.from(getEntity(id));
    }

    public EmployeDto.Response create(EmployeDto.Request r) {
        Employe e = new Employe();
        apply(e, r);
        return EmployeDto.Response.from(employeRepository.save(e));
    }

    public EmployeDto.Response update(Long id, EmployeDto.Request r) {
        Employe e = getEntity(id);
        apply(e, r);
        return EmployeDto.Response.from(e);
    }

    public void delete(Long id) {
        employeRepository.delete(getEntity(id));
    }

    private Employe getEntity(Long id) {
        return employeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employe", id));
    }

    private void apply(Employe e, EmployeDto.Request r) {
        e.setNom(r.nom());
        e.setPrenom(r.prenom());
        e.setRole(r.role());
        e.setAgence(agenceService.getEntity(r.idAgence()));
    }
}
