package com.esprit.gestiondesconges.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;
    private String salaire;
    private String password;
    private String equipe;
    private String manager;
    private int soldeConges;

    public Long getIdemployee() {
        return id;
    }

    public void setIdemployee(Long id) {
        this.id = id;
    }
}
