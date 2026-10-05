package com.example.autoloc.dto;

import com.example.autoloc.entities.Agence;
import jakarta.validation.constraints.NotBlank;

public final class AgenceDto {

    private AgenceDto() {
    }

    public record Request(
            @NotBlank String nom,
            @NotBlank String ville,
            String adresse,
            String telephone) {
    }

    public record Response(Long idAgence, String nom, String ville, String adresse, String telephone) {
        public static Response from(Agence a) {
            return new Response(a.getIdAgence(), a.getNom(), a.getVille(), a.getAdresse(), a.getTelephone());
        }
    }
}
