package com.esprit.gestiondesconges.services.interfaces;

import com.esprit.gestiondesconges.entities.Event;
import com.esprit.gestiondesconges.entities.TypeEvent;

import java.util.Date;
import java.util.List;

public interface IEventService {
    Event ajouterEvent(Event event);
    Event supprimerEvent(Long idEvent);
    Event récupérerEvent(Long idEvent);
    Event modifierEvent(Long idEvent, Event event);
    List<Event> récupérerListeEvents();
    List<Event> searchEvents(String keyword, TypeEvent typeEvent, Date startDate, Date endDate);
    Event récupérerEventById(Long idEvent);
}
