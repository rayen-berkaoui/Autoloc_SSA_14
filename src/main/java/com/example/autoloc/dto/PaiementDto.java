package com.example.autoloc.dto;

import com.example.autoloc.entities.Paiement;
import com.example.autoloc.entities.enums.ModePaiement;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public final class PaiementDto {

    private PaiementDto() {
    }

    /** {@code datePaiement} is optional: defaults to today. */
    public record Request(
            @NotNull @Positive BigDecimal montant,
            LocalDate datePaiement,
            @NotNull ModePaiement modePaiement,
            @NotNull Long idContrat) {
    }

    public record Response(Long idPaiement, BigDecimal montant, LocalDate datePaiement,
                           ModePaiement modePaiement, Long idContrat) {
        public static Response from(Paiement p) {
            return new Response(p.getIdPaiement(), p.getMontant(), p.getDatePaiement(),
                    p.getModePaiement(), p.getContrat().getIdContrat());
        }
    }
}
