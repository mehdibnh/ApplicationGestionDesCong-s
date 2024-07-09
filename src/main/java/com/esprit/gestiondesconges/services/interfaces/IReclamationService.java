package com.esprit.gestiondesconges.services.interfaces;

import com.esprit.gestiondesconges.entities.Reclamation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface IReclamationService {
    Reclamation ajouterReclamation(Reclamation reclamation);
    Reclamation supprimerReclamation(Long idReclamation);
    Reclamation recupererReclamation(Long idReclamation);
    Reclamation modifierReclamation(Long idReclamation, Reclamation reclamation);
    List<Reclamation> recupererListeReclamation();

    Map<String, Long> nombreReclamationsParCategorie();
    long nombreReclamationsParEmploye(Long idEmployee);

    @Transactional
    void archiverReclamationManuellement(Long idReclamation);

    List<Reclamation> getSortedData(String sortField, String sortOrder);

    Map<String, Object> getStatistics();
}
