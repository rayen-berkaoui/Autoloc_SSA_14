package com.example.autoloc.service;

import com.example.autoloc.dto.EquipementDto;
import com.example.autoloc.entities.Equipement;
import com.example.autoloc.exception.ResourceNotFoundException;
import com.example.autoloc.repository.EquipementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EquipementService {

    private final EquipementRepository equipementRepository;
    private final VehicleService vehicleService;

    @Transactional(readOnly = true)
    public List<EquipementDto.Response> findAll(Long idVehicule) {
        List<Equipement> list = idVehicule == null
                ? equipementRepository.findAll()
                : equipementRepository.findByVehiculeIdVehicule(idVehicule);
        return list.stream().map(EquipementDto.Response::from).toList();
    }

    @Transactional(readOnly = true)
    public EquipementDto.Response findById(Long id) {
        return EquipementDto.Response.from(getEntity(id));
    }

    public EquipementDto.Response create(EquipementDto.Request r) {
        Equipement e = new Equipement();
        apply(e, r);
        return EquipementDto.Response.from(equipementRepository.save(e));
    }

    public EquipementDto.Response update(Long id, EquipementDto.Request r) {
        Equipement e = getEntity(id);
        apply(e, r);
        return EquipementDto.Response.from(e);
    }

    public void delete(Long id) {
        equipementRepository.delete(getEntity(id));
    }

    private Equipement getEntity(Long id) {
        return equipementRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Equipement", id));
    }

    private void apply(Equipement e, EquipementDto.Request r) {
        e.setLibelle(r.libelle());
        e.setVehicule(vehicleService.getEntity(r.idVehicule()));
    }
}
