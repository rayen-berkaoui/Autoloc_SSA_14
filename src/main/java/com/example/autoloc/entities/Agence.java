package com.example.autoloc.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "agence")
@Getter
@Setter
@NoArgsConstructor
public class Agence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAgence;

    private String nom;

    private String ville;

    private String adresse;

    private String telephone;

    @OneToMany(mappedBy = "agence")
    private List<Employe> employes = new ArrayList<>();

    @OneToMany(mappedBy = "agence")
    private List<Vehicle> vehicules = new ArrayList<>();
}
