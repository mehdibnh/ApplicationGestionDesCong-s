package com.esprit.gestiondesconges.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
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
    private String nom;
    private String prenom;
    private String email;
    private Date dateNaissance;
    private Date dateRecrutement;
    private String salaire;
    private String password;

    @Enumerated(EnumType.STRING)
    private TypeRole role;

    private int soldeConge;

    @ManyToOne
    private Equipe equipe;

    @ManyToOne
    private Employee manager;

    @OneToMany(mappedBy = "employee")
    private Set<Conge> conges;

    @OneToMany(mappedBy = "employee")
    private Set<Reclamation> reclamations;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "employee")
    private Set<Historique> historique;

    public void setSoldeConge(int soldeConge) {
        this.soldeConge = soldeConge;
    }
}
