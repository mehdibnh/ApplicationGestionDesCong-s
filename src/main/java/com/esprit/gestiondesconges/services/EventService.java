package com.esprit.gestiondesconges.services;

import com.esprit.gestiondesconges.entities.Event;
import com.esprit.gestiondesconges.repositories.IEventRepo;
import com.esprit.gestiondesconges.services.interfaces.IEventService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Slf4j
@Service
public class EventService implements IEventService {
    IEventRepo eventRepo;
    @Override
    public Event ajouterEvent(Event event) {
        return eventRepo.save(event);
    }

    @Override
    public Event supprimerEvent(Long idEvent) {
        Event event = récupérerEvent(idEvent);
        if (event != null) {
            eventRepo.delete(event);
        }
        return event;
    }

    @Override
    public Event récupérerEvent(Long idEvent) {
        return eventRepo.findById(idEvent).orElse(null);
    }

    @Override
    public Event modifierEvent(Long idEvent, Event event) {
        if (eventRepo.existsById(idEvent)) {
            event.setIdEvent(idEvent);
            return eventRepo.save(event);
        }
        return null;
    }

    @Override
    public List<Event> récupérerListeEvents() {
        return eventRepo.findAll();
    }
}
