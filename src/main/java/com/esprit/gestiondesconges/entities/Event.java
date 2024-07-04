package com.esprit.gestiondesconges.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@Getter
@Setter
@ToString
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEvent;
    private String nomEvent;
    private Date dateDebutEvent;
    private Date dateFinEvent;
    private int nombreDeJours;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private TypeEvent typeEvent;



}
