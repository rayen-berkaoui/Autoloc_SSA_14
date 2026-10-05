package com.example.autoloc.dto;

import com.example.autoloc.entities.Equipement;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public final class EquipementDto {

    private EquipementDto() {
    }

    public record Request(@NotBlank String libelle, @NotNull Long idVehicule) {
    }

    public record Response(Long idEquipement, String libelle, Long idVehicule) {
        public static Response from(Equipement e) {
            return new Response(e.getIdEquipement(), e.getLibelle(), e.getVehicule().getIdVehicule());
        }
    }
}
