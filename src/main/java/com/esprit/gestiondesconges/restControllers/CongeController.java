package com.esprit.gestiondesconges.restControllers;

import com.esprit.gestiondesconges.entities.Conge;
import com.esprit.gestiondesconges.repositories.IConge;
import com.esprit.gestiondesconges.services.interfaces.ICongeServices;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/conge")
public class CongeController {
    ICongeServices congeServices;
    @PostMapping("/ajouter")
    public Conge ajouterConge(@RequestBody Conge conge) {
        return congeServices.ajouterConge(conge);
    }

    @DeleteMapping("/supprimer/{idConge}")
    public Conge supprimerConge(@PathVariable("idConge") Long idconge) {
        return
                congeServices.supprimerConge(idconge);
    }

    @GetMapping("/recuperer/{idConge}")
    public Conge recupererConge(@PathVariable("idConge") Long idconge) {
        return congeServices.recupererConge(idconge);
    }

    @PutMapping("/modifier/{idConge}")
    public Conge modifierConge(@PathVariable("idConge") Long idconge, @RequestBody Conge conge) {
        return congeServices.modifierConge(idconge, conge);
    }

    @GetMapping("/liste")
    public List<Conge> recupererListeConge() {
        return congeServices.recupererListeConge();
    }
}
