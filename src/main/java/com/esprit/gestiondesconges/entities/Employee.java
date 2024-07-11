package com.esprit.gestiondesconges.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

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
    private Role role;

    private int soldeConge;


    @ManyToOne
    @JoinColumn(name = "equipe_id")
    @JsonBackReference
    private Equipe equipe;

    @ManyToOne
    private Employee manager;

    @OneToMany(mappedBy = "employee")
    private Set<Conge> conges;

    @OneToMany(mappedBy = "employee")
    private Set<Reclamation> reclamations;
    @OneToMany(mappedBy = "employee")
    private Set<ArchivedReclamation> archivedReclamations;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "employee")
    private Set<Historique> historique;

    public void setSoldeConge(int soldeConge) {
        this.soldeConge = soldeConge;
    }
}
