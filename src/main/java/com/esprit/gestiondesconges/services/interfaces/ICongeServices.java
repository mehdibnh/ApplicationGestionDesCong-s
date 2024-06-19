package com.esprit.gestiondesconges.services.interfaces;

import com.esprit.gestiondesconges.entities.Conge;
import java.util.List;

public interface ICongeServices {
    Conge ajouterConge(Conge conge);
    Conge supprimerConge(Long idconge);
    Conge recupererConge(Long idconge);
    Conge modifierConge(Long idconge, Conge conge);
    List<Conge> recupererListeConge();
    Conge accepterconge(Long idconge);
    Conge refuser(Long idconge);
    List<Conge> annuler();
    Conge effecteremployeraconge (Long idconge , Long idemployer);
}
