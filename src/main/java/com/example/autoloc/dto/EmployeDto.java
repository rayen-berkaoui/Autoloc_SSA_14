package com.example.autoloc.dto;

import com.example.autoloc.entities.Employe;
import com.example.autoloc.entities.enums.RoleEmploye;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public final class EmployeDto {

    private EmployeDto() {
    }

    public record Request(
            @NotBlank String nom,
            @NotBlank String prenom,
            @NotNull RoleEmploye role,
            @NotNull Long idAgence) {
    }

    public record Response(Long idEmploye, String nom, String prenom, RoleEmploye role, Long idAgence) {
        public static Response from(Employe e) {
            return new Response(e.getIdEmploye(), e.getNom(), e.getPrenom(), e.getRole(),
                    e.getAgence() != null ? e.getAgence().getIdAgence() : null);
        }
    }
}
