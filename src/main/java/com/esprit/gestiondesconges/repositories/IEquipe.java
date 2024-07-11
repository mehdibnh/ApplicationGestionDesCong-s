package com.esprit.gestiondesconges.repositories;
import com.esprit.gestiondesconges.entities.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IEquipe extends JpaRepository<Equipe,Long> {
}
