package com.esprit.gestiondesconges.services.interfaces;

import com.esprit.gestiondesconges.entities.Conge;
import com.esprit.gestiondesconges.entities.EtatConge;
import com.esprit.gestiondesconges.entities.Historique;

import java.util.List;

public interface IHistoriqueService {
    List<Historique> getAllHistorique();
    List<Historique> getHistoriqueByUsername(String username);
    List<Historique> getHistoriqueByAction(EtatConge action);
    Historique createHistoriqueEntry(Conge conge );
    public Historique editHistoriqueEntryById(long idConge, Conge newCongeData);

    void deletehistorique(long idConge);
}
