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
    private long idConge;
    private Date dateDebut;
    private Date dateFin;
    private int nombreDeJours;
    @Enumerated(EnumType.STRING)
    private Status status ;
    private String status;
    @Enumerated(EnumType.STRING)
    TypeStatut statut;
    @Enumerated(EnumType.STRING)
    TypeConge typeConge;
    boolean certifie;
    @JsonIgnore
    @OneToMany
    private Set<Historique> historiques;

    @ManyToOne
    @JsonIgnore
    private Employee employee;

    public void setStatus(Status status) {
        this.status = status;
    }
}

}

