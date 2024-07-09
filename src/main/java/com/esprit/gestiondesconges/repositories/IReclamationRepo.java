package com.esprit.gestiondesconges.repositories;

import com.esprit.gestiondesconges.entities.Reclamation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface IReclamationRepo extends JpaRepository<Reclamation, Long> {
    List<Reclamation> findByDateCreationBefore(LocalDateTime limite);
    void deleteById(Long idReclamation);
}
