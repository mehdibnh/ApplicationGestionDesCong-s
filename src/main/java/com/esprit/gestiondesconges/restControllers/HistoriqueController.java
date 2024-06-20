package com.esprit.gestiondesconges.restControllers;

import com.esprit.gestiondesconges.entities.EtatConge;
import com.esprit.gestiondesconges.entities.Historique;
import com.esprit.gestiondesconges.services.HistoriqueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/historique")
public class HistoriqueController {

    private final HistoriqueService historiqueService;

    public HistoriqueController(HistoriqueService historiqueService) {
        this.historiqueService = historiqueService;
    }

    @GetMapping
    public ResponseEntity<List<Historique>> getAllHistorique() {
        List<Historique> historique = historiqueService.getAllHistorique();
        return ResponseEntity.ok().body(historique);
    }

    @GetMapping("/byUsername")
    public ResponseEntity<List<Historique>> getHistoriqueByUsername(@RequestParam String nom) {
        List<Historique> historique = historiqueService.getHistoriqueByUsername(nom);
        return ResponseEntity.ok().body(historique);
    }

    @GetMapping("/byAction")
    public ResponseEntity<List<Historique>> getHistoriqueByAction(@RequestParam EtatConge action) {
        List<Historique> historique = historiqueService.getHistoriqueByAction(action);
        return ResponseEntity.ok().body(historique);
    }

    /*@PostMapping("/create")
    public ResponseEntity<Historique> createHistoriqueEntry(@RequestBody Historique historique) {
        Historique createdHistorique = historiqueService.createHistoriqueEntry(
                historique.getEtatConge(),
                historique.getUsername(), // Ensure the correct getter is used
                historique.getStatus()
        );
        return ResponseEntity.ok().body(createdHistorique);
    }*/
}
