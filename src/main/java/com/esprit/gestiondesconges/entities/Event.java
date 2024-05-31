package com.esprit.gestiondesconges.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEvent;
    private String nomEvent;
    private Date dateDebutEvent;
    private Date dateFinEvent;
    private int nombreDeJours;
    private TypeEvent typeEvent;



}
