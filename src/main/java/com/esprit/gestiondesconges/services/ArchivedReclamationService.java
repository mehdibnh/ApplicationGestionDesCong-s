package com.esprit.gestiondesconges.services;

import com.esprit.gestiondesconges.entities.ArchivedReclamation;
import com.esprit.gestiondesconges.repositories.IArchivedReclamationRepo;
import com.esprit.gestiondesconges.services.interfaces.IArchivedReclamationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@Service
@Slf4j
public class ArchivedReclamationService implements IArchivedReclamationService {
    IArchivedReclamationRepo archivedReclamationRepo;

    @Override
    public List<ArchivedReclamation> recupererToutesLesArchivedReclamations() {
        return archivedReclamationRepo.findAll();
    }

    @Override
    public void supprimerArchivedReclamation(Long id) {
        archivedReclamationRepo.deleteById(id);
    }

    @Override
    public void scheduleDeletion(Long idArchivedReclamation) {
        CompletableFuture.delayedExecutor(7, TimeUnit.DAYS).execute(() -> {
            supprimerArchivedReclamation(idArchivedReclamation);
        });
    }
}
