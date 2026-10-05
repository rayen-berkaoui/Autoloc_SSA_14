package com.example.autoloc.dto;

import com.example.autoloc.entities.Client;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public final class ClientDto {

    private ClientDto() {
    }

    public record Request(
            @NotBlank String nom,
            @NotBlank String prenom,
            @NotBlank @Email String email,
            String telephone,
            @NotBlank String numPermis) {
    }

    public record Response(Long idClient, String nom, String prenom, String email, String telephone,
                           String numPermis, LocalDate dateInscription) {
        public static Response from(Client c) {
            return new Response(c.getIdClient(), c.getNom(), c.getPrenom(), c.getEmail(),
                    c.getTelephone(), c.getNumPermis(), c.getDateInscription());
        }
    }
}
