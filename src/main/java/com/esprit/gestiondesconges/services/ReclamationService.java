package com.esprit.gestiondesconges.services;

import com.esprit.gestiondesconges.entities.ArchivedReclamation;
import com.esprit.gestiondesconges.entities.Reclamation;
import com.esprit.gestiondesconges.repositories.IArchivedReclamationRepo;
import com.esprit.gestiondesconges.repositories.IReclamationRepo;
import com.esprit.gestiondesconges.services.interfaces.IReclamationService;
import com.esprit.gestiondesconges.services.interfaces.IArchivedReclamationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
@AllArgsConstructor
@Service
@Slf4j
public class ReclamationService implements IReclamationService {
    public IReclamationRepo reclamationRepo;
    public IArchivedReclamationRepo archivedReclamationRepo;
    private final IArchivedReclamationService archivedReclamationService;

    @Override
    public Reclamation ajouterReclamation(Reclamation reclamation) {
        Reclamation savedReclamation = reclamationRepo.save(reclamation);
        scheduleArchiving(savedReclamation);
        return savedReclamation;
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

    @Override
    public Map<String, Long> nombreReclamationsParCategorie() {
        return reclamationRepo.findAll().stream().collect(Collectors.groupingBy(Reclamation::getCategorie, Collectors.counting()));
    }

    @Override
    public long nombreReclamationsParEmploye(Long idEmployee) {
        return reclamationRepo.findAll().stream()
                .filter(reclamation -> reclamation.getEmployee().getIdEmployee().equals(idEmployee))
                .count();
    }

    @Override
    @Transactional
    public void archiverReclamationManuellement(Long idReclamation) {
        Reclamation reclamation = recupererReclamation(idReclamation);
        if (reclamation != null) {
            ArchivedReclamation archivedReclamation = new ArchivedReclamation(
                    null, // idArchivedReclamation will be generated by the database
                    reclamation.getIdReclamation(),
                    reclamation.getTitre(),
                    reclamation.getStatus(),
                    reclamation.getEmployee(),
                    reclamation.getCategorie(),
                    reclamation.getDateCreation(),
                    LocalDateTime.now(),
                    reclamation.getDescription()  // Pass the description
            );
            archivedReclamation.setReclamation(null);  // Dissociate the relationship
            ArchivedReclamation savedArchivedReclamation = archivedReclamationRepo.save(archivedReclamation);
            reclamationRepo.delete(reclamation); // Delete the original reclamation after saving the archive
            archivedReclamationService.scheduleDeletion(savedArchivedReclamation.getIdArchivedReclamation()); // Schedule deletion
        }
    }

    public void scheduleArchiving(Reclamation reclamation) {
        CompletableFuture.delayedExecutor(7, TimeUnit.DAYS).execute(() -> {
            archiverReclamationManuellement(reclamation.getIdReclamation());
        });
    }

    // Méthode pour le tri personnalisé
    public List<Reclamation> getSortedData(String sortField, String sortOrder) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortField);
        return reclamationRepo.findAll(sort);
    }

    // Méthode pour récupérer les statistiques
    public Map<String, Object> getStatistics() {
        long totalReclamations = reclamationRepo.count();
        long archivedReclamations = archivedReclamationRepo.count();

        Map<String, Long> reclamationsByCategory = reclamationRepo.findAll().stream()
                .collect(Collectors.groupingBy(Reclamation::getCategorie, Collectors.counting()));

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalReclamations", totalReclamations);
        stats.put("archivedReclamations", archivedReclamations);
        stats.put("reclamationsByCategory", reclamationsByCategory);

        return stats;
    }
}
