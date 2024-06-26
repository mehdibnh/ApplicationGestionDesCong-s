package com.esprit.gestiondesconges.restControllers;

import com.esprit.gestiondesconges.entities.Event;
import com.esprit.gestiondesconges.services.interfaces.IEventService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/event")

public class EventRestController {
    IEventService eventService;

    @PostMapping("/ajouter")
    public Event ajouterEvent(@RequestBody Event event) {
        return eventService.ajouterEvent(event);
    }

    @DeleteMapping("/supprimer/{idEvent}")
    public Event supprimerEvent(@PathVariable Long idEvent) {
        return eventService.supprimerEvent(idEvent);
    }

    @GetMapping("/récupérer/{idEvent}")
    public Event récupérerEvent(@PathVariable Long idEvent) {
        return eventService.récupérerEvent(idEvent);
    }

    @PutMapping("/modifier/{idEvent}")
    public Event modifierEvent(@PathVariable Long idEvent, @RequestBody Event event) {
        return eventService.modifierEvent(idEvent, event);
    }

    @GetMapping("/liste")
    public List<Event> récupérerListeEvents() {
        return eventService.récupérerListeEvents();
    }
}
