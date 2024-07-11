package com.esprit.gestiondesconges.services.interfaces;

import com.esprit.gestiondesconges.entities.ArchivedReclamation;

import java.util.List;

public interface IArchivedReclamationService {
    List<ArchivedReclamation> recupererToutesLesArchivedReclamations();
    void supprimerArchivedReclamation(Long id);
    void scheduleDeletion(Long idArchivedReclamation);  // Ajout de la méthode
}
