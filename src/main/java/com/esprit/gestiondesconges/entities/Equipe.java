package com.esprit.gestiondesconges.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private String projet;
    private String departement;

    @OneToMany(mappedBy = "equipe", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonManagedReference
    private Set<Employee> employees;

    @OneToOne
    @JoinColumn(name = "teamLeader_id")
    @JsonManagedReference
    private Employee teamLeader;
}
