package com.example.autoloc.entities;

import com.example.autoloc.entities.enums.RoleEmploye;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "employe")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmploye;

    private String nom;

    private String prenom;

    @Enumerated(EnumType.STRING)
    private RoleEmploye role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_agence")
    private Agence agence;
}
