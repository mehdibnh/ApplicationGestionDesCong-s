package com.esprit.gestiondesconges.repositories;

import com.esprit.gestiondesconges.entities.ArchivedReclamation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IArchivedReclamationRepo extends JpaRepository<ArchivedReclamation, Long> {
}
