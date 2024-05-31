package com.esprit.gestiondesconges.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

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
    private String password;

    private int soldeConge;

}