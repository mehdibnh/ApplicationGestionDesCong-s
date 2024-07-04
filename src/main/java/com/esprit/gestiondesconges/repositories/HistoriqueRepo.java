package com.esprit.gestiondesconges.repositories;

import com.esprit.gestiondesconges.entities.EtatConge;
import com.esprit.gestiondesconges.entities.Historique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.esprit.gestiondesconges.entities.Historique;

import java.util.List;
import java.util.Optional;


@Repository
    public interface HistoriqueRepo extends JpaRepository<Historique, Long> {


    List<Historique> findHistoriqueByEmployee_Nom(String nom);
    List<Historique> findByEtatConge(EtatConge action);

    @Query("SELECT h FROM Historique h WHERE h.idHistorique = :id")
    Optional<Historique> findHistoriqueById(@Param("id") Long id);


}





