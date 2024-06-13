package com.esprit.gestiondesconges.repositories;

import com.esprit.gestiondesconges.entities.Historique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.esprit.gestiondesconges.entities.Historique;



    @Repository
    public interface HistoriqueRepo extends JpaRepository<Historique, Long> {




}