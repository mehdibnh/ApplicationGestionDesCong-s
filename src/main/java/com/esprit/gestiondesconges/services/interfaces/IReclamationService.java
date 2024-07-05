package com.esprit.gestiondesconges.services.interfaces;

import com.esprit.gestiondesconges.entities.Reclamation;

import java.util.List;

//methode de crude
public interface IReclamationService {
    Reclamation ajouterReclamation(Reclamation reclamation);
    Reclamation supprimerReclamation(Long idReclamation);
    Reclamation recupererReclamation(Long idReclamation);
    Reclamation modifierReclamation(Long idReclamation, Reclamation reclamation);
    List<Reclamation> recupererListeReclamation();
}
