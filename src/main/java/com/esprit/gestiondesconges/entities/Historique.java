package com.esprit.gestiondesconges.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.lang.reflect.Type;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class Historique {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idconge;
    private Long idemployee;
    private Typeconge typeconge;
    private String Statut;
}

