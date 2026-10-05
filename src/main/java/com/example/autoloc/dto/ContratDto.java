package com.example.autoloc.dto;

import com.example.autoloc.entities.Contrat;
import com.example.autoloc.entities.Paiement;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public final class ContratDto {

    private ContratDto() {
    }

    /** The contract amount is computed from the reservation, not provided by the caller. */
    public record Request(@NotNull Long idReservation) {
    }

    public record Response(Long idContrat, LocalDate dateSignature, BigDecimal montantTotal, boolean valide,
                           Long idReservation, BigDecimal totalPaye, BigDecimal resteAPayer) {
        public static Response from(Contrat c) {
            BigDecimal paye = c.getPaiements().stream()
                    .map(Paiement::getMontant)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            return new Response(c.getIdContrat(), c.getDateSignature(), c.getMontantTotal(), c.isValide(),
                    c.getReservation().getIdReservation(), paye, c.getMontantTotal().subtract(paye));
        }
    }
}
