package com.esprit.gestiondesconges.services;
import com.esprit.gestiondesconges.entities.Conge;
import com.esprit.gestiondesconges.entities.Event;
import com.esprit.gestiondesconges.repositories.IConge;
import com.esprit.gestiondesconges.repositories.IEventRepo;
import com.esprit.gestiondesconges.services.interfaces.ICongeServices;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@AllArgsConstructor
@Slf4j
@Service
public class CongeServices  implements ICongeServices {
   IConge congeRepo;


    @Override
    public Conge ajouterConge(Conge conge) {
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
