package com.esprit.gestiondesconges.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;

public class Conge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idConge;
    private Date dateDebut;
    private Date dateFin;
    private int nombreDeJours;
    private TypeConge typeConge;
    private boolean certifié;
}
