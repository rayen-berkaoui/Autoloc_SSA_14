package com.example.autoloc.service;

import com.example.autoloc.dto.MaintenanceDto;
import com.example.autoloc.entities.Maintenance;
import com.example.autoloc.entities.Vehicle;
import com.example.autoloc.exception.BusinessException;
import com.example.autoloc.exception.ResourceNotFoundException;
import com.example.autoloc.repository.MaintenanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;
    private final VehicleService vehicleService;
    private final VehicleStatusService statusService;

    @Transactional(readOnly = true)
    public List<MaintenanceDto.Response> findAll(Long idVehicule) {
        List<Maintenance> list = idVehicule == null
                ? maintenanceRepository.findAll()
                : maintenanceRepository.findByVehiculeIdVehicule(idVehicule);
        return list.stream().map(MaintenanceDto.Response::from).toList();
    }

    @Transactional(readOnly = true)
    public MaintenanceDto.Response findById(Long id) {
        return MaintenanceDto.Response.from(getEntity(id));
    }

    public MaintenanceDto.Response create(MaintenanceDto.Request r) {
        Maintenance m = new Maintenance();
        apply(m, r);
        maintenanceRepository.save(m);
        statusService.refresh(m.getVehicule());
        return MaintenanceDto.Response.from(m);
    }

    public MaintenanceDto.Response update(Long id, MaintenanceDto.Request r) {
        Maintenance m = getEntity(id);
        Vehicle ancien = m.getVehicule();
        apply(m, r);
        statusService.refresh(ancien);
        statusService.refresh(m.getVehicule());
        return MaintenanceDto.Response.from(m);
    }

    public void delete(Long id) {
        Maintenance m = getEntity(id);
        Vehicle vehicle = m.getVehicule();
        maintenanceRepository.delete(m);
        statusService.refresh(vehicle);
    }

    private Maintenance getEntity(Long id) {
        return maintenanceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Maintenance", id));
    }

    private void apply(Maintenance m, MaintenanceDto.Request r) {
        if (r.dateFin() != null && r.dateFin().isBefore(r.dateDebut())) {
            throw new BusinessException("dateFin ne peut pas être avant dateDebut");
        }
        m.setDateDebut(r.dateDebut());
        m.setDateFin(r.dateFin());
        m.setDescription(r.description());
        m.setVehicule(vehicleService.getEntity(r.idVehicule()));
    }
}
