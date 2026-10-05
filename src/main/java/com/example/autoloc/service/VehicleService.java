package com.example.autoloc.service;

import com.example.autoloc.dto.VehicleDto;
import com.example.autoloc.entities.Vehicle;
import com.example.autoloc.entities.enums.StatutVehicule;
import com.example.autoloc.exception.ConflictException;
import com.example.autoloc.exception.ResourceNotFoundException;
import com.example.autoloc.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final AgenceService agenceService;

    @Transactional(readOnly = true)
    public List<VehicleDto.Response> findAll(StatutVehicule statut, Long idAgence) {
        List<Vehicle> vehicles;
        if (statut != null && idAgence != null) {
            vehicles = vehicleRepository.findByStatutAndAgenceIdAgence(statut, idAgence);
        } else if (statut != null) {
            vehicles = vehicleRepository.findByStatut(statut);
        } else if (idAgence != null) {
            vehicles = vehicleRepository.findByAgenceIdAgence(idAgence);
        } else {
            vehicles = vehicleRepository.findAll();
        }
        return vehicles.stream().map(VehicleDto.Response::from).toList();
    }

    @Transactional(readOnly = true)
    public VehicleDto.Response findById(Long id) {
        return VehicleDto.Response.from(getEntity(id));
    }

    public VehicleDto.Response create(VehicleDto.Request r) {
        if (vehicleRepository.existsByImmatriculation(r.immatriculation())) {
            throw new ConflictException("Immatriculation déjà utilisée : " + r.immatriculation());
        }
        Vehicle v = new Vehicle();
        v.setStatut(StatutVehicule.DISPONIBLE);
        apply(v, r);
        return VehicleDto.Response.from(vehicleRepository.save(v));
    }

    public VehicleDto.Response update(Long id, VehicleDto.Request r) {
        Vehicle v = getEntity(id);
        if (!v.getImmatriculation().equals(r.immatriculation())
                && vehicleRepository.existsByImmatriculation(r.immatriculation())) {
            throw new ConflictException("Immatriculation déjà utilisée : " + r.immatriculation());
        }
        apply(v, r);
        return VehicleDto.Response.from(v);
    }

    public void delete(Long id) {
        vehicleRepository.delete(getEntity(id));
    }

    Vehicle getEntity(Long id) {
        return vehicleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Vehicule", id));
    }

    private void apply(Vehicle v, VehicleDto.Request r) {
        v.setImmatriculation(r.immatriculation());
        v.setMarque(r.marque());
        v.setModele(r.modele());
        v.setCategorie(r.categorie());
        v.setTarifJournalier(r.tarifJournalier());
        v.setAgence(agenceService.getEntity(r.idAgence()));
    }
}
