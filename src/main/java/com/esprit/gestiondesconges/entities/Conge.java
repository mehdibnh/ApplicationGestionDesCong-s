package com.esprit.gestiondesconges.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private long idConge;
    private Date dateDebut;
    private Date dateFin;
    private int nombreDeJours;
    @Enumerated(EnumType.STRING)
    private TypeConge typeConge;
    private boolean certifié;
    @JsonIgnore
    @ManyToMany
    private Set<Historique> historiques;
    @JsonIgnore
    @ManyToOne
    private Employee employee;
}
