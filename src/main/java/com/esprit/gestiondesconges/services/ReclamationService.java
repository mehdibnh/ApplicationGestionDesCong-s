package com.esprit.gestiondesconges.services;

import com.esprit.gestiondesconges.entities.Reclamation;
import com.esprit.gestiondesconges.repositories.IReclamationRepo;
import com.esprit.gestiondesconges.services.interfaces.IReclamationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@AllArgsConstructor
@Service
@Slf4j
public class ReclamationService implements IReclamationService {
    IReclamationRepo reclamationRepo;

    @Override
    public Reclamation ajouterReclamation(Reclamation reclamation) {
        return reclamationRepo.save(reclamation);
    }

    @Override
    public Reclamation supprimerReclamation(Long idReclamation) {
        Reclamation reclamation = recupererReclamation(idReclamation);
        if (reclamation != null) {
            reclamationRepo.delete(reclamation);
        }
        return reclamation;
    }

    @Override
    public Reclamation recupererReclamation(Long idReclamation) {
        return reclamationRepo.findById(idReclamation).orElse(null);
    }

    @Override
    public Reclamation modifierReclamation(Long idReclamation, Reclamation reclamation) {
        if (reclamationRepo.existsById(idReclamation)) {
            reclamation.setIdReclamation(idReclamation);
            return reclamationRepo.save(reclamation);
        }
        return null;
    }

    @Override
    public List<Reclamation> recupererListeReclamation() {
        return reclamationRepo.findAll();
    }
}
