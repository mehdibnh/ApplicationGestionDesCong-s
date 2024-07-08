package com.esprit.gestiondesconges.restControllers;

import com.esprit.gestiondesconges.entities.Conge;
import com.esprit.gestiondesconges.entities.EtatConge;
import com.esprit.gestiondesconges.entities.Historique;
import com.esprit.gestiondesconges.services.CongeServices;
import com.esprit.gestiondesconges.services.HistoriqueService;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/historique")
public class HistoriqueController {

    private final HistoriqueService historiqueService;
    private final CongeServices congeServices;


    public HistoriqueController(HistoriqueService historiqueService, CongeServices congeServices) {
        this.historiqueService = historiqueService;
        this.congeServices = congeServices;
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
 /*   @PutMapping("/edit/{idHistorique}")
    public ResponseEntity<Historique> editHistoriqueEntryById(@PathVariable Long idHistorique) {
        Historique editedHistorique = historiqueService.editHistoriqueEntryById(idHistorique);
        return ResponseEntity.ok().body(editedHistorique);
    }*/
   /* @GetMapping("historiqueid")
    public ResponseEntity<Historique> editHistoriqueEntry(@PathParam("id") long id) {
        Historique editedHistorique = historiqueService.editHistoriqueEntryById(id);
        return ResponseEntity.ok().body(editedHistorique);
    }*/
    @PutMapping("/edit/{idConge}")
    public ResponseEntity<Historique> editHistoriqueEntryById(
            @PathVariable long idConge, @RequestBody Conge newCongeData) {
        try {
            Historique updatedHistorique = historiqueService.editHistoriqueEntryById(idConge, newCongeData);
            return ResponseEntity.ok(updatedHistorique);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
