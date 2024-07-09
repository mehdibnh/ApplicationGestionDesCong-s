package com.esprit.gestiondesconges.restControllers;

import com.esprit.gestiondesconges.entities.ArchivedReclamation;
import com.esprit.gestiondesconges.services.interfaces.IArchivedReclamationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/archived-reclamation")
@CrossOrigin("*")
public class ArchivedReclamationController {
    private final IArchivedReclamationService archivedReclamationService;

    @GetMapping("/liste")
    public List<ArchivedReclamation> recupererToutesLesArchivedReclamations() {
        return archivedReclamationService.recupererToutesLesArchivedReclamations();
    }

    @DeleteMapping("/supprimer/{id}")
    public void supprimerArchivedReclamation(@PathVariable Long id) {
        archivedReclamationService.supprimerArchivedReclamation(id);
    }
}
