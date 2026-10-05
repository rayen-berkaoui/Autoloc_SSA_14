package com.example.autoloc.dto;

import com.example.autoloc.entities.Vehicle;
import com.example.autoloc.entities.enums.CategorieVehicule;
import com.example.autoloc.entities.enums.StatutVehicule;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public final class VehicleDto {

    private VehicleDto() {
    }

    /** The status is not part of the request: it is computed from maintenances and reservations. */
    public record Request(
            @NotBlank String immatriculation,
            @NotBlank String marque,
            @NotBlank String modele,
            @NotNull CategorieVehicule categorie,
            @NotNull @Positive BigDecimal tarifJournalier,
            @NotNull Long idAgence) {
    }

    public record Response(Long idVehicule, String immatriculation, String marque, String modele,
                           CategorieVehicule categorie, BigDecimal tarifJournalier,
                           StatutVehicule statut, Long idAgence) {
        public static Response from(Vehicle v) {
            return new Response(v.getIdVehicule(), v.getImmatriculation(), v.getMarque(), v.getModele(),
                    v.getCategorie(), v.getTarifJournalier(), v.getStatut(),
                    v.getAgence() != null ? v.getAgence().getIdAgence() : null);
        }
    }
}
