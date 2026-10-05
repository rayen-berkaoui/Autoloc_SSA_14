package com.example.autoloc.dto;

import com.example.autoloc.entities.Reservation;
import com.example.autoloc.entities.enums.StatutReservation;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public final class ReservationDto {

    private ReservationDto() {
    }

    public record Request(
            @NotNull LocalDate dateDebut,
            @NotNull LocalDate dateFin,
            @NotNull Long idClient,
            @NotNull Long idVehicule) {
    }

    public record StatutRequest(@NotNull StatutReservation statut) {
    }

    public record Response(Long idReservation, LocalDate dateDebut, LocalDate dateFin,
                           StatutReservation statut, Long idClient, Long idVehicule) {
        public static Response from(Reservation r) {
            return new Response(r.getIdReservation(), r.getDateDebut(), r.getDateFin(), r.getStatut(),
                    r.getClient().getIdClient(), r.getVehicule().getIdVehicule());
        }
    }
}
