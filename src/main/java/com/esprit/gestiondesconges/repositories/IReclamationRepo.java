package com.esprit.gestiondesconges.repositories;

import com.esprit.gestiondesconges.entities.Event;
import com.esprit.gestiondesconges.entities.Reclamation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IReclamationRepo extends JpaRepository<Reclamation,Long> {

}
