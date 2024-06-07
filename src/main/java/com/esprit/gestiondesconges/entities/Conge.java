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
public class Conge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idConge;
    private Date dateDebut;
    private Date dateFin;
    private int nombreDeJours;
    private TypeConge typeConge;
    private boolean certifié;

    @ManyToMany
    private Set<Historique> historiques;

    @ManyToOne
    private Employee employee;
}
