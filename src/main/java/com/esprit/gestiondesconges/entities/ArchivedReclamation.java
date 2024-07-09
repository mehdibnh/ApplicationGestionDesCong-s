package com.esprit.gestiondesconges.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArchivedReclamation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idArchivedReclamation;

    private Long idOriginalReclamation;
    private String titre;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    private Employee employee;

    private String categorie;

    private LocalDateTime dateCreation;
    private LocalDateTime dateArchivage;
    private String description;

    @OneToOne
    @JoinColumn(name = "reclamation_id")
    private Reclamation reclamation;


    public ArchivedReclamation(Long idArchivedReclamation, Long idOriginalReclamation, String titre, Status status, Employee employee, String categorie, LocalDateTime dateCreation, LocalDateTime dateArchivage, String description) {
        this.idArchivedReclamation = idArchivedReclamation;
        this.idOriginalReclamation = idOriginalReclamation;
        this.titre = titre;
        this.status = status;
        this.employee = employee;
        this.categorie = categorie;
        this.dateCreation = dateCreation;
        this.dateArchivage = dateArchivage;
        this.description = description;
    }
}
