package com.esprit.gestiondesconges.repositories;

import com.esprit.gestiondesconges.entities.Event;
import com.esprit.gestiondesconges.entities.TypeEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IEventRepo extends JpaRepository<Event,Long> {
    @Query("SELECT e FROM Event e WHERE " +
            "(:keyword IS NULL OR e.nomEvent LIKE %:keyword%) AND " +
            "(:typeEvent IS NULL OR e.typeEvent = :typeEvent) AND " +
            "(:startDate IS NULL OR e.dateDebutEvent >= :startDate) AND " +
            "(:endDate IS NULL OR e.dateFinEvent <= :endDate)")
    List<Event> findByCriteria(@Param("keyword") String keyword,
                               @Param("typeEvent") TypeEvent typeEvent,
                               @Param("startDate") Date startDate,
                               @Param("endDate") Date endDate);

    @Query("SELECT e FROM Event e WHERE e.dateDebutEvent >= :startOfDay AND e.dateDebutEvent < :endOfDay")
    List<Event> findEventsBetween(@Param("startOfDay") Date startOfDay, @Param("endOfDay") Date endOfDay);

}
