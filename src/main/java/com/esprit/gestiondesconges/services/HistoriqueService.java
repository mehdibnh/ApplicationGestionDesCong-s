package com.esprit.gestiondesconges.services;

import com.esprit.gestiondesconges.UserContexte;
import com.esprit.gestiondesconges.entities.Conge;
import com.esprit.gestiondesconges.entities.EtatConge;
import com.esprit.gestiondesconges.entities.Historique;
import com.esprit.gestiondesconges.entities.StatusConge;
import com.esprit.gestiondesconges.repositories.HistoriqueRepo;
import com.esprit.gestiondesconges.services.interfaces.IHistoriqueService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class HistoriqueService implements IHistoriqueService {

    private final HistoriqueRepo historiqueRepository;

    public HistoriqueService(HistoriqueRepo historiqueRepository) {
        this.historiqueRepository = historiqueRepository;
    }

    @Override
    public List<Historique> getAllHistorique() {
        return historiqueRepository.findAll();
    }

    @Override
    public List<Historique> getHistoriqueByUsername(String username) {
        return historiqueRepository.findHistoriqueByEmployee_Nom(username);
    }

    @Override
    public List<Historique> getHistoriqueByAction(EtatConge action) {
        return historiqueRepository.findByEtatConge(action);
    }

    @Override
    public Historique createHistoriqueEntry(Conge conge) {
        Historique historique = new Historique();
        historique.setConge(conge);
        historique.setAction(EtatConge.EnAttente);
        historique.setActionTimestamp(LocalDateTime.now());
        historique.setUsername(UserContexte.getUsername());
        historique.setStatus(StatusConge.cree);
        return historiqueRepository.save(historique);
    }
}

