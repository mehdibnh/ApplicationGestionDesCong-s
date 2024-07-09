package com.esprit.gestiondesconges.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Historique {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHistorique;

    private Date dateModification;
    private TypeOperations typeOperations;
    private String description;

    @JsonIgnore
    @ManyToOne
    private Employee employee;
    private Conge conge;
    @Enumerated(EnumType.STRING)
    private StatusConge statusconge;

    @Enumerated(EnumType.STRING)
    private EtatConge etatConge;

    private LocalDateTime timestamp;

    public void setAction(EtatConge action) {
        this.etatConge = action;
    }

    public void setActionTimestamp(LocalDateTime actionTimestamp) {
        this.timestamp = actionTimestamp;
    }

    public void setUsername(String username) {
        if (this.employee == null) {
            this.employee = new Employee();
        }
        this.employee.setNom(username);
    }

    public void setStatus(StatusConge status) {
        this.statusconge = status;
    }

    public void setStatusConge(StatusConge status) {
        this.statusconge = status;
    }


    public String getUsername() {
        return employee.getNom();
    }

    public void setConge(Conge conge) {
        this.conge = conge;
    }
}
