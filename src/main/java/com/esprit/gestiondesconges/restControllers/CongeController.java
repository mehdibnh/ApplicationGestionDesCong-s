package com.esprit.gestiondesconges.restControllers;

import com.esprit.gestiondesconges.entities.Conge;
import com.esprit.gestiondesconges.services.interfaces.ICongeServices;
import com.esprit.gestiondesconges.services.interfaces.IHistoriqueService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/conge")
public class CongeController {
    ICongeServices congeServices;
    IHistoriqueService historiqueService;
    @PostMapping("/ajouter")
    public Conge ajouterConge(@RequestBody Conge conge) {
        Conge congeAjoute = congeServices.ajouterConge(conge);

        historiqueService.createHistoriqueEntry(conge);
        return conge;
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

    @PutMapping("/accepter/{idConge}")
    public Conge accepterconge(@PathVariable("idConge") Long idconge) {
        return congeServices.accepterconge(idconge);
    }
    @PutMapping("/refuse/{idConge}")
    public Conge refuser(@PathVariable("idConge") Long idconge) {
        return congeServices.refuser(idconge);
    }

    @PutMapping("/annuler")
    public List<Conge> annuler() {
        return congeServices.annuler();
    }

    @PostMapping(path = "/affecteremployer/{idconge}/{idemployer}")
    public Conge affecterFoyerAuniversite(@PathVariable Long idconge, @PathVariable Long idemployer) {
        {
            return congeServices.effecteremployeraconge (idconge ,idemployer);
        }
    }
}