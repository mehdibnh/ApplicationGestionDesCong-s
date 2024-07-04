package com.esprit.gestiondesconges.restControllers;

import com.esprit.gestiondesconges.entities.Reclamation;
import com.esprit.gestiondesconges.services.interfaces.IReclamationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/reclamation")
public class ReclamationRestControler {
    IReclamationService reclamationService;
    @PostMapping("/ajouter")
    public Reclamation ajouterReclamation(Reclamation reclamation) {
        return reclamationService.ajouterReclamation(reclamation);
    }

    @DeleteMapping("/supprimer/{idReclamation}")
    public Reclamation supprimerReclamation(@PathVariable Long idReclamation){ return reclamationService.supprimerReclamation(idReclamation); }

    @GetMapping("/recuperer/{idReclamation}")

    public Reclamation recupererReclamation(@PathVariable Long idReclamation) {return reclamationService.recupererReclamation(idReclamation);}


    @PutMapping("/modifier/{idReclamation}")
    public Reclamation modifierReclamation(@PathVariable Long idReclamation, @RequestBody Reclamation reclamation) {return reclamationService.modifierReclamation (idReclamation, reclamation);}
}
