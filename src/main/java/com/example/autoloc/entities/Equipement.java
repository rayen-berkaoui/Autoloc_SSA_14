package com.example.autoloc.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "equipement")
@Getter
@Setter
@NoArgsConstructor
public class Equipement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEquipement;

    private String libelle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vehicule", nullable = false)
    private Vehicle vehicule;
}
