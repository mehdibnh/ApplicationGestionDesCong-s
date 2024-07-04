package com.esprit.gestiondesconges.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmployee;
    private  String nom;
    private  String prenom;
    private  String email;
    private Date dateNaissance;
    private Date dateRecrutement;
    private  String salaire;
    @Enumerated(EnumType.STRING)
    private  TypeRole role;
    private String password;
    private int soldeConge;

    @ManyToOne
    private Equipe equipe;

    @ManyToOne
    private Employee employee;
    @OneToMany(mappedBy = "employee")
    private Set<Conge> conges;
    @OneToMany(mappedBy = "employee")
    private Set<Reclamation> reclamations;



    public void setSoldeConge(int soldeConge) {
        this.soldeConge = soldeConge;
    }
}