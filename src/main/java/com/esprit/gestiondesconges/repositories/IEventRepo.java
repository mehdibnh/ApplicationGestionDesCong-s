package com.esprit.gestiondesconges.repositories;

import com.esprit.gestiondesconges.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEventRepo extends JpaRepository<Event,Long> {

}
