package com.esprit.gestiondesconges.services;


import com.esprit.gestiondesconges.entities.Event;
import com.esprit.gestiondesconges.entities.Employee;
import com.esprit.gestiondesconges.entities.TypeEvent;
import com.esprit.gestiondesconges.repositories.IEventRepo;
import com.esprit.gestiondesconges.services.interfaces.IEventService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Slf4j
@Service
public class EventService implements IEventService {
    IEventRepo eventRepo;
    EmployeeService employeeService;

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

    @Override
    public List<Event> searchEvents(String keyword, TypeEvent typeEvent, Date startDate, Date endDate) {
        if (keyword == null && typeEvent == null && startDate == null && endDate == null) {
            return eventRepo.findAll();
        }

        return eventRepo.findByCriteria(keyword, typeEvent, startDate, endDate);
    }

    @Override
    public Event récupérerEventById(Long idEvent) {
        return eventRepo.getById(idEvent);
    }

    //@Scheduled(cron = "0 0 9 * * ?") // Tous les jours à 9 heures du matin
    //@Scheduled(cron = "0 * * * * ?")
   /* @Scheduled(fixedRate = 20000)
    public void checkEventsAndSendEmails() {
        //Date tomorrow = getTomorrowDate();
        Date startOfDay = getStartOfDay();
        Date endOfDay = getEndOfDay();
        //System.out.println(tomorrow);
        System.out.println(eventRepo.findAll());
        try {
            emailService.sendEmail(
                    //employe.getNom()+"@mail.com",
                    "yogogot975@elahan.com",
                    "Reminder: Upcoming Event",
                    "Dear ,\n\n" +
                            "This is a reminder that the event '' will take place tomorrow.\n\n" +
                            "Best regards,\n" +
                            "Your Company"
            );
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        List<Event> eventsTomorrow = eventRepo.findEventsBetween(startOfDay, endOfDay);
        System.out.println(eventsTomorrow);
        if (!eventsTomorrow.isEmpty()) {
            List<Employee> employes = employeeService.getAllEmployees();
            for (Event event : eventsTomorrow) {
                for (Employee employe : employes) {
                    try {
                        emailService.sendEmail(
                                //employe.getNom()+"@mail.com",
                                "yogogot975@elahan.com",
                                "Reminder: Upcoming Event",
                                "Dear " + employe.getNom() + ",\n\n" +
                                        "This is a reminder that the event '" + event.getNomEvent() + "' will take place tomorrow.\n\n" +
                                        "Best regards,\n" +
                                        "Your Company"
                        );
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }*/
    /*private Date getTomorrowDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        return calendar.getTime();
    }*/

    private Date getStartOfDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    private Date getEndOfDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
}
