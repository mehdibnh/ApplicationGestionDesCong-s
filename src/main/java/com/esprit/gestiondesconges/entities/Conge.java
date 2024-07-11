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
    private   Date dateFin;
    private int nombreDeJours;
    @Enumerated(EnumType.STRING)
    private Status status ;
    @Enumerated(EnumType.STRING)
    Typeconge typeConge;
    @Column(columnDefinition = "boolean default false")
    private boolean certifié ;
    @OneToMany
    private Set<Historique> historiques;

    @ManyToOne
    private Employee employee;

    public void setStatus(Status status) {
        this.status = status;
    }


}