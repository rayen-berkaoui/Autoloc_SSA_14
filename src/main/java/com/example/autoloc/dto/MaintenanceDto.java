package com.example.autoloc.dto;

import com.example.autoloc.entities.Maintenance;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public final class MaintenanceDto {

    private MaintenanceDto() {
    }

    /** {@code dateFin} may be null while the maintenance is still ongoing. */
    public record Request(
            @NotNull LocalDate dateDebut,
            LocalDate dateFin,
            String description,
            @NotNull Long idVehicule) {
    }

    public record Response(Long idMaintenance, LocalDate dateDebut, LocalDate dateFin,
                           String description, Long idVehicule) {
        public static Response from(Maintenance m) {
            return new Response(m.getIdMaintenance(), m.getDateDebut(), m.getDateFin(),
                    m.getDescription(), m.getVehicule().getIdVehicule());
        }
    }
}
