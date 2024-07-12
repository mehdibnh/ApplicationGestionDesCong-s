package com.esprit.gestiondesconges.services;

import com.esprit.gestiondesconges.EmailService;
import com.esprit.gestiondesconges.entities.Conge;
import com.esprit.gestiondesconges.entities.Employee;
import com.esprit.gestiondesconges.entities.Status;
import com.esprit.gestiondesconges.entities.StatusConge;
import com.esprit.gestiondesconges.repositories.EmployeeRepo;
import com.esprit.gestiondesconges.entities.TypeRole;
import com.esprit.gestiondesconges.entities.TypeStatut;
import com.esprit.gestiondesconges.repositories.ICongeRepo;
import com.esprit.gestiondesconges.repositories.IEmployerRepo;
import com.esprit.gestiondesconges.services.interfaces.ICongeServices;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
@Service

public class CongeServices  implements ICongeServices {
    ICongeRepo congeRepo;
    EmployeeRepo emplpoerRepo ;
    HistoriqueService historiqueService;
    EmailService  emailService ;

    @Override
    public Conge ajouterConge(Conge conge) {
        conge.setStatus(Status.En_attente);
        // Conserver cette ligne
        // Optional <Employee> e= emplpoerRepo.findById(conge.getEmployee().getIdEmployee());
        // if (e.isPresent()){conge.setEmployee(e.get());}

        long idemployer = 1;
        Employee employee = emplpoerRepo.findById(idemployer).orElse(null);
        conge.setEmployee(employee);
        conge.setStatut(TypeStatut.enattente);
        java.time.LocalDate dateDebut = conge.getDateDebut().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        java.time.LocalDate dateFin = conge.getDateFin().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if (dateDebut.isBefore(LocalDate.now())) {
            return null;
        }
        long totalDays = ChronoUnit.DAYS.between(dateDebut, dateFin) + 1;
        long weekendDays = 0;
        for (LocalDate date = dateDebut; !date.isAfter(dateFin); date = date.plusDays(1)) {
            if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                weekendDays++;
            }
        }
        long workingDays = totalDays - weekendDays;
        if (workingDays > 0) {
            conge.setNombreDeJours((int) workingDays);
        }
        Conge savedConge = congeRepo.save(conge);
        System.out.println("Conge saved: " + savedConge);  // Ajoutez cette ligne pour journaliser la réponse
        return savedConge;
    }

    @Override
    public Conge supprimerConge(Long idconge) {
        Conge conge = recupererConge(idconge);
        if (conge != null) {
            congeRepo.delete(conge);
            // historiqueService.deletehistorique(idconge);
        }
        return conge;
    }

    @Override
    public Conge recupererConge(Long idconge) {
        return congeRepo.findById(idconge).orElse(null);
    }

    @Override
    public Conge modifierConge(Long idconge, Conge conge) {
        if (congeRepo.existsById(idconge)) {
            conge.setStatus(Status.En_attente);
            java.time.LocalDate dateDebut = conge.getDateDebut().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            java.time.LocalDate dateFin = conge.getDateFin().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            long nombreDeJours = ChronoUnit.DAYS.between(dateDebut, dateFin);
            conge.setNombreDeJours((int) nombreDeJours);
            conge.setIdConge(idconge);
            historiqueService.editHistoriqueEntryById(idconge, conge);
            return congeRepo.save(conge);
        }
        return null;
    }

    @Override
    public List<Conge> recupererListeConge() {
        return congeRepo.findAll();
    }

    @Override
    public List<Conge> recupererListeCongeenvoyerparunemployer(Long idemployer) {
        Employee e = emplpoerRepo.findById(idemployer).orElse(null);
        if (e != null) {
            return congeRepo.getAllByEmployee(e);
        } else {
            return null;
        }
    }

    @Override
    public Conge accepterconge(Long idconge) {
        Conge conge = congeRepo.findById(idconge).orElse(null);
        if (conge != null) {
            conge.setStatus(Status.accepter);
            // ici, effectuer la soustraction du nombre de jours disponibles de l'employé.
            int soldedecongedeemployer = conge.getEmployee().getSoldeConge();
            int nbjourconge = conge.getNombreDeJours();
            Long idemployer = conge.getEmployee().getIdEmployee();
            Employee employee = emplpoerRepo.findById(idemployer).orElse(null);
            if (conge != null && conge.getStatut() == TypeStatut.enattente && soldedecongedeemployer >= nbjourconge) {
                conge.setStatut(TypeStatut.accepter);
                employee.setSoldeConge(soldedecongedeemployer - nbjourconge);
                emplpoerRepo.save(employee);
                congeRepo.save(conge);
                String subject = "Conge Accepter";
                String message = String.format("Bonjour"+conge.getEmployee().getNom(),conge.getEmployee().getPrenom(),"Votre Conge est accepter avec success","**");
                emailService.sendEmail(conge.getEmployee().getEmail(), subject, message); // Impleme
            }
            return conge;
        }
        return conge;
    }

    @Override
    public Conge refuser(Long idconge) {
        Conge conge = congeRepo.findById(idconge).orElse(null);
        // ici, effectuer la soustraction du nombre de jours disponibles de l'employé.
        if (conge != null) {
            conge.setStatus(Status.refuse);
            conge.setStatut(TypeStatut.refuser);
            Long idemployer = conge.getEmployee().getIdEmployee();
            Employee employee = emplpoerRepo.findById(idemployer).orElse(null);
            congeRepo.save(conge);
            String subject = "Conge Refuser";
            String message = String.format("Bonjour"+conge.getEmployee().getNom(),conge.getEmployee().getPrenom(),"Votre Conge est refuse","**");
            emailService.sendEmail(conge.getEmployee().getEmail(), subject, message); // Impleme
        }
        return conge;
    }

    @Override
    @Scheduled(cron = "0 0 0 * * ?")
    public List<Conge> annuler() {
        LocalDate dateSysteme = LocalDate.now();
        List<Conge> tousLesConges = congeRepo.findAll();
        List<Conge> congesAAnnuler = tousLesConges.stream()
                .filter(conge -> {
                    LocalDate dateDebutConge = conge.getDateDebut().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    return dateDebutConge != null &&
                            dateDebutConge.getMonth() == dateSysteme.getMonth() &&
                            dateDebutConge.getDayOfMonth() == dateSysteme.getDayOfMonth();
                })
                .collect(Collectors.toList());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        congesAAnnuler.forEach(conge -> {
            String dateDebutFormatee = conge.getDateDebut().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(formatter);
            String dateFinFormatee = conge.getDateFin().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(formatter);
            if (conge.getStatut() == TypeStatut.enattente) {
                conge.setStatus(Status.annuler);
                conge.setStatut(TypeStatut.annuler);
                congeRepo.save(conge);
            }
        });
        return congesAAnnuler;
    }

    @Override
    public Conge effecteremployeraconge(Long idconge, Long idemployer) {
        return null;
    }
}
