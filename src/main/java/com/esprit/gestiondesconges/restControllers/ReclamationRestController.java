package com.esprit.gestiondesconges.restControllers;

import com.esprit.gestiondesconges.entities.Reclamation;
import com.esprit.gestiondesconges.services.interfaces.IReclamationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@RestController
@AllArgsConstructor
@CrossOrigin("*")
@RequestMapping("/reclamation")
public class ReclamationRestController {
    IReclamationService reclamationService;

    @PostMapping("/ajouter")
    public Reclamation ajouterReclamation(@RequestBody Reclamation reclamation) {
        return reclamationService.ajouterReclamation(reclamation);
    }

    @DeleteMapping("/supprimer/{idReclamation}")
    public Reclamation supprimerReclamation(@PathVariable Long idReclamation) {
        return reclamationService.supprimerReclamation(idReclamation);
    }

    @GetMapping("/recuperer/{idReclamation}")
    public Reclamation recupererReclamation(@PathVariable Long idReclamation) {
        return reclamationService.recupererReclamation(idReclamation);
    }

    @PutMapping("/modifier/{idReclamation}")
    public Reclamation modifierReclamation(@PathVariable Long idReclamation, @RequestBody Reclamation reclamation) {
        return reclamationService.modifierReclamation(idReclamation, reclamation);
    }

    @GetMapping("/liste")
    public List<Reclamation> recupererListeReclamation() {
        return reclamationService.recupererListeReclamation();
    }

    @GetMapping("/statistiques/categorie")
    public Map<String, Long> nombreReclamationsParCategorie() {
        return reclamationService.nombreReclamationsParCategorie();
    }

    @GetMapping("/statistiques/employe/{idEmployee}")
    public long nombreReclamationsParEmploye(@PathVariable Long idEmployee) {
        return reclamationService.nombreReclamationsParEmploye(idEmployee);
    }

    @PostMapping("/archiver")
    public void archiverReclamationManuellement(@RequestParam Long idReclamation) {
        reclamationService.archiverReclamationManuellement(idReclamation);
    }

    // Nouveau point de terminaison pour le tri
    @GetMapping("/sorted-data")
    public List<Reclamation> getSortedData(@RequestParam String sortField, @RequestParam String sortOrder) {
        return reclamationService.getSortedData(sortField, sortOrder);
    }

    // Nouveau point de terminaison pour les statistiques
    @GetMapping("/statistics")
    public Map<String, Object> getStatistics() {
        return reclamationService.getStatistics();
    }
}