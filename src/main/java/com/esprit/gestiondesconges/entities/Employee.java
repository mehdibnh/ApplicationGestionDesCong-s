package com.esprit.gestiondesconges.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmployee;


@OneToMany(fetch = FetchType.EAGER, mappedBy = "employee")
private Set <Historique> historique;
    private String nom;
    private String prenom;
    private String salaire;
    private String password;
    private String equipe;
    private String manager;
    private double soldeConges;
    @Enumerated(EnumType.STRING)
    private TRole role;


}
