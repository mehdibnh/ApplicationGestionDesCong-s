package com.esprit.gestiondesconges.restControllers;

import com.esprit.gestiondesconges.entities.Equipe;
import com.esprit.gestiondesconges.services.interfaces.IEquipeServices;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/equipe")
@CrossOrigin("*")
public class EquipeController {

    private final IEquipeServices equipeServices;

    @PostMapping("/ajouter")
    public Equipe ajouterEquipe(@RequestBody Equipe equipe) {
        return equipeServices.ajouterEquipe(equipe);
    }

    @DeleteMapping("/supprimer/{idEquipe}")
    public Equipe supprimerEquipe(@PathVariable("idEquipe") Long idEquipe) {
        return equipeServices.supprimerEquipe(idEquipe);
    }

    @GetMapping("/recuperer/{idEquipe}")
    public Equipe recupererEquipe(@PathVariable("idEquipe") Long idEquipe) {
        return equipeServices.recupererEquipe(idEquipe);
    }

    @PutMapping("/modifier/{idEquipe}")
    public Equipe modifierEquipe(@PathVariable("idEquipe") Long idEquipe, @RequestBody Equipe equipe) {
        return equipeServices.modifierEquipe(idEquipe, equipe);
    }

    @GetMapping("/liste")
    public List<Equipe> recupererListeEquipe() {
        return equipeServices.recupererListeEquipe();
    }

    @PostMapping("/assignTeamLeader")
    public ResponseEntity<String> assignTeamLeader(@RequestParam Long equipeId, @RequestParam Long employeeId) {
        String result = equipeServices.assignTeamLeader(equipeId, employeeId);
        if (result.equals("Team leader assigned successfully")) {
            // Get the email of the assigned team leader and send notification
            try {
                equipeServices.sendTeamLeaderNotification(equipeId, employeeId);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send notification");
            }
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/addMembers")
    public ResponseEntity<String> addMembersToEquipe(@RequestParam Long equipeId, @RequestBody List<Long> employeeIds) {
        String result = equipeServices.addMembersToEquipe(equipeId, employeeIds);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{equipeId}")
    public ResponseEntity<?> deleteEquipe(@PathVariable Long equipeId) {
        try {
            equipeServices.deleteEquipe(equipeId);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{idEquipe}")
    public ResponseEntity<Equipe> updateEquipe(@PathVariable Long idEquipe, @RequestBody Equipe equipeDetails) {
        Equipe updatedEquipe = equipeServices.updateEquipe(idEquipe, equipeDetails);
        return ResponseEntity.ok(updatedEquipe);
    }
}
