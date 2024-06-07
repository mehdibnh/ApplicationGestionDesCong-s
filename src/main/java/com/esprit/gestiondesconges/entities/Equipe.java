package com.esprit.gestiondesconges.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Equipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEquipe;
    private String nomEquipe;
    private Long nombrePersonnes;

    @ManyToOne
    private Departement departement;

    @OneToMany(mappedBy = "equipe")
    private Set<Employee> employees;

}
