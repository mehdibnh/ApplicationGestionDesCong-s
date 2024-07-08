package com.esprit.gestiondesconges.services;

import com.esprit.gestiondesconges.entities.Conge;
import com.esprit.gestiondesconges.entities.EtatConge;
import com.esprit.gestiondesconges.entities.Historique;
import com.esprit.gestiondesconges.entities.StatusConge;
import com.esprit.gestiondesconges.repositories.HistoriqueRepo;
import com.esprit.gestiondesconges.repositories.ICongeRepo;
import com.esprit.gestiondesconges.services.interfaces.IHistoriqueService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HistoriqueService implements IHistoriqueService {

    private final HistoriqueRepo historiqueRepository;
    private final ICongeRepo iConge;

    public HistoriqueService(HistoriqueRepo historiqueRepository, ICongeRepo iConge) {
        this.historiqueRepository = historiqueRepository;
        this.iConge = iConge;
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
        // historique.setUsername(UserContexte.getUsername());
        // historique.setUsername(conge.getEmployee().getNom());
        historique.setEmployee(conge.getEmployee());
        historique.setStatus(StatusConge.cree);

        return historiqueRepository.save(historique);
    }

    @Override
    public Historique editHistoriqueEntryById(long idConge, Conge newCongeData) {
        // Récupérer l'objet Historique à partir de la base de données
        List<Historique> historiqueList = historiqueRepository.findByCongeId(idConge);

        if (historiqueList.isEmpty()) {
            throw new RuntimeException("Historique not found for Conge ID: " + idConge);
        }

        Historique existingHistorique = historiqueList.stream().findFirst().get();
        Conge existingConge = existingHistorique.getConge();
        boolean isModified = false;

        // Check and update dateDebut
        if (!existingConge.getDateDebut().equals(newCongeData.getDateDebut())) {
            existingConge.setDateDebut(newCongeData.getDateDebut());
            isModified = true;
        }

        // Check and update dateFin
        if (!existingConge.getDateFin().equals(newCongeData.getDateFin())) {
            existingConge.setDateFin(newCongeData.getDateFin());
            isModified = true;
        }

        // Check and update nombreDeJours
        if (existingConge.getNombreDeJours() != newCongeData.getNombreDeJours()) {
            existingConge.setNombreDeJours(newCongeData.getNombreDeJours());
            isModified = true;
        }

        // Check and update typeConge
        if (existingConge.getTypeConge() != newCongeData.getTypeConge()) {
            existingConge.setTypeConge(newCongeData.getTypeConge());
            isModified = true;
        }

        // Check and update status
        if (!existingConge.getStatus().equals(newCongeData.getStatus())) {
            existingConge.setStatus(newCongeData.getStatus());
            isModified = true;
        }

        // Update statusConge if any change detected
        if (isModified) {
            existingHistorique.setStatusConge(StatusConge.modifer); // Assuming StatusConge is an Enum
        }

        // Save the updated Conge
        iConge.save(existingConge);
        // Save the updated Historique
        historiqueRepository.save(existingHistorique);

        return existingHistorique;
    @Override
    public Historique editHistoriqueEntryById(Long idHistorique) {
        // Logique pour éditer une entrée de l'historique par ID (à implémenter)
        return null;
    }
    @Override
    public void deletehistorique(long idConge) {
        // Récupérer le congé à supprimer
        Conge congToDelete = iConge.findById(idConge)
                .orElseThrow(() -> new RuntimeException("Congé not found with ID: " + idConge));

        // Créer une entrée dans la table 'historique' avec statut "supprimer"
        Historique historique = new Historique();
        historique.setTimestamp(LocalDateTime.now());
        historique.setStatus(StatusConge.supprimé);
        historique.setConge(congToDelete); // Ajouter le congé supprimé à l'historique
        historique.setStatusConge(StatusConge.supprimé); // Assumer que StatusConge est un Enum

        historiqueRepository.save(historique);

        // Supprimer le congé de la table 'co' après l'enregistrement dans l'historique
        iConge.deleteById(idConge);
    public long deletehistorique(long idConge) {
        long optionalConge = Long.parseLong(null);  // il faut corrige
        // Mettre à jour la date de modification
        // Sauvegarder les modifications dans la base de données
        // Créer et retourner un objet Historique pour cette action
        return optionalConge;
    }
}
