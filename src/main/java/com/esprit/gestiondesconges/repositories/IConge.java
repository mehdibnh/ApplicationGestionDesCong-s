package com.esprit.gestiondesconges.repositories;

import com.esprit.gestiondesconges.entities.Conge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IConge extends JpaRepository<Conge,Long> {


}
