package com.esprit.gestiondesconges.services;

import com.esprit.gestiondesconges.entities.Conge;
import com.esprit.gestiondesconges.entities.Employee;
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
public class CongeServices implements ICongeServices {
    ICongeRepo congeRepo;
    IEmployerRepo emplpoerRepo;

    @Override
    public Conge ajouterConge(Conge conge) {
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
            return congeRepo.save(conge);
        }
        return congeRepo.save(conge);
    }

    @Override
    public Conge supprimerConge(Long idconge) {
        Conge conge = recupererConge(idconge);
        if (conge != null) {
            congeRepo.delete(conge);
        }
        return conge;
    }

    @Override
    public Conge recupererConge(Long idconge) {
        return congeRepo.findById(idconge).orElse(null);
    }

    @Override
    public Conge modifierConge(Long idconge, Conge conge) {
        Conge conge1 = congeRepo.findById(idconge).orElse(null);
        if (conge1 != null && conge1.getStatut() == TypeStatut.enattente) {
            if (conge.getDateDebut() == null || conge.getDateFin() == null) {
                conge1.setTypeConge(conge.getTypeConge());
                congeRepo.save(conge1);
                return conge1;
            } else {
                java.time.LocalDate dateDebut = conge.getDateDebut().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                java.time.LocalDate dateFin = conge.getDateFin().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                if (dateDebut.isBefore(LocalDate.now())) {
                    return null;
                }
                long nombreDeJours = ChronoUnit.DAYS.between(dateDebut, dateFin) + 1;
                if (nombreDeJours > 0) {
                    conge.setNombreDeJours((int) nombreDeJours);
                    conge1.setNombreDeJours((int) nombreDeJours);
                    conge1.setIdConge(idconge);
                    conge1.setDateDebut(conge.getDateDebut());
                    conge1.setDateFin(conge.getDateFin());
                    congeRepo.save(conge1);
                    return conge1;
                }
            }
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
        int soldedecongedeemployer = conge.getEmployee().getSoldeConge();
        int nbjourconge = conge.getNombreDeJours();
        Long idemployer = conge.getEmployee().getIdEmployee();
        Employee employee = emplpoerRepo.findById(idemployer).orElse(null);
        if (conge != null && conge.getStatut() == TypeStatut.enattente && soldedecongedeemployer >= nbjourconge) {
            conge.setStatut(TypeStatut.accepter);
            employee.setSoldeConge(soldedecongedeemployer - nbjourconge);
            emplpoerRepo.save(employee);
            congeRepo.save(conge);
        }
        return conge;
    }

    @Override
    public Conge refuser(Long idconge) {
        Conge conge = congeRepo.findById(idconge).orElse(null);
        if (conge != null) {
            conge.setStatut(TypeStatut.refuser);
            Long idemployer = conge.getEmployee().getIdEmployee();
            Employee employee = emplpoerRepo.findById(idemployer).orElse(null);
            congeRepo.save(conge);
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
