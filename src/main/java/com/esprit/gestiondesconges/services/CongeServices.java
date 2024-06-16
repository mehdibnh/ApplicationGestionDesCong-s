package com.esprit.gestiondesconges.services;
import com.esprit.gestiondesconges.entities.Conge;
import com.esprit.gestiondesconges.repositories.IConge;
import com.esprit.gestiondesconges.services.interfaces.ICongeServices;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.time.temporal.ChronoUnit;
import java.time.ZoneId;

@AllArgsConstructor
@Slf4j
@Service
public class CongeServices  implements ICongeServices {
   IConge congeRepo;


    @Override
    public Conge ajouterConge(Conge conge) {
        conge.setStatus("en attente");

        // Convertir les dates en LocalDate
        java.time.LocalDate dateDebut = conge.getDateDebut().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        java.time.LocalDate dateFin = conge.getDateFin().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        // Calculer le nombre de jours entre dateDebut et dateFin
        long nombreDeJours = ChronoUnit.DAYS.between(dateDebut, dateFin);

        // Mettre à jour l'attribut nombreDeJours
        conge.setNombreDeJours((int) nombreDeJours);
        return congeRepo.save(conge);
    }
    @Override
    public Conge supprimerConge(Long idconge) {
        Conge conge = recupererConge(idconge);
        if (conge != null) {
            congeRepo.delete(conge);
        }
        return conge;
    }

    @Override
    public Conge recupererConge(Long idconge) {
        return congeRepo.findById(idconge).orElse(null);
    }

    @Override
    public Conge modifierConge(Long idconge, Conge conge) {
        if (congeRepo.existsById(idconge)) {
            conge.setIdConge(idconge);
            return congeRepo.save(conge);
        }
        return null;
    }

    @Override
    public List<Conge> recupererListeConge() {
        return congeRepo.findAll();
    }
}
