package com.esprit.gestiondesconges.services;
import com.esprit.gestiondesconges.entities.Conge;
import com.esprit.gestiondesconges.entities.Employee;
import com.esprit.gestiondesconges.repositories.ICongeRepo;
import com.esprit.gestiondesconges.repositories.IEmployerRepo;
import com.esprit.gestiondesconges.services.interfaces.ICongeServices;
import com.esprit.gestiondesconges.entities.TypeStatut ;
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
import java.util.List;
import java.time.temporal.ChronoUnit;
import java.time.ZoneId;
import java.util.Properties;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
@Service
public class CongeServices  implements ICongeServices {
    ICongeRepo congeRepo;
    IEmployerRepo emplpoerRepo ;
    

    @Override
    public Conge ajouterConge(Conge conge) {
       // sette la conge a la paremier employer il fauter change ca par la employer conncter
        long idemployer = 1 ;
        Employee employee = emplpoerRepo.findById(idemployer).orElse(null);
        conge.setEmployee(employee);
        // ca
        conge.setStatut(TypeStatut.enattente);
        java.time.LocalDate dateDebut = conge.getDateDebut().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        java.time.LocalDate dateFin = conge.getDateFin().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        long totalDays = ChronoUnit.DAYS.between(dateDebut, dateFin) + 1; // inclure dateDebut et dateFin
        long weekendDays = 0;
        for (LocalDate date = dateDebut; !date.isAfter(dateFin); date = date.plusDays(1)) {
            if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                weekendDays++;
            }
        }
        long workingDays = totalDays - weekendDays;
       //  workingDays = workingDays + 1 ;
        conge.setNombreDeJours((int) workingDays);
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
        Conge conge1 = new Conge() ;
        conge1 = congeRepo.findById(idconge).orElse(null);
        if (conge1.getStatut()==TypeStatut.enattente) {

            if ((conge.getDateDebut()==null )||(conge.getDateFin()==null))
            {
                conge1.setTypeConge(conge.getTypeConge());
                congeRepo.save(conge1);
                return  conge1 ;
            }else
                System.out.println();
                java.time.LocalDate dateDebut = conge.getDateDebut().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                java.time.LocalDate dateFin = conge.getDateFin().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                long nombreDeJours = ChronoUnit.DAYS.between(dateDebut, dateFin);
                nombreDeJours = nombreDeJours + 1;
                conge1.setNombreDeJours((int) nombreDeJours);
                conge1.setIdConge(idconge);
                conge1.setDateDebut(conge.getDateDebut());
                conge1.setDateFin(conge.getDateFin());
                congeRepo.save(conge1);
                return conge1;
        }
        return  conge1 ;
    }

    @Override
    public List<Conge> recupererListeConge() {
        return congeRepo.findAll();
    }
    @Override
    public Conge accepterconge(Long idconge) {
        Conge conge = congeRepo.findById(idconge).orElse(null);
            // applele fonction loggin
         int  soldedecongedeemployer = conge.getEmployee().getSoldeConge();
         int  nbjourconge = conge.getNombreDeJours() ;
         Long idemployer = conge.getEmployee().getIdEmployee();
         Employee employee = emplpoerRepo.findById(idemployer).orElse(null);
        if (conge != null && conge.getStatut()==TypeStatut.enattente && soldedecongedeemployer>=nbjourconge) {
            System.out.println(conge.getEmployee().getEmail());
            // apelle fonction envoyer mail
            System.out.println("7777777777777777777777");
            conge.setStatut(TypeStatut.accepter);
            employee.setSoldeConge(soldedecongedeemployer-nbjourconge);
            emplpoerRepo.save(employee) ;
            congeRepo.save(conge);
        }
        return conge;
    }
    @Override
    public Conge refuser(Long idconge) {
        Conge conge = congeRepo.findById(idconge).orElse(null);
        if (conge != null ) {
            conge.setStatut(TypeStatut.refuser);
            Long idemployer = conge.getEmployee().getIdEmployee();
            Employee employee = emplpoerRepo.findById(idemployer).orElse(null);
            congeRepo.save(conge);
        }
        return conge;
    }
    @Override
 //   @Scheduled(cron = "0 * * * * ?")  debut de chaque menute
    @Scheduled(cron = "0 0 0 * * ?")  // debut de chaque jour
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
          if (conge.getStatut()==TypeStatut.enattente)
          {
            conge.setStatut(TypeStatut.annuler);
            congeRepo.save(conge);
          }
        });
        return congesAAnnuler;
    }

    @Override
    public Conge effecteremployeraconge(Long idconge, Long idemployer) {
        Conge conge = congeRepo.findById(idconge).orElse(null);
        Employee employee = emplpoerRepo.findById(idemployer).orElse(null);
        if (conge != null && employee != null) {
        //    conge.setEmployee(employee);
            congeRepo.save(conge);
            return conge;
        } else {
            log.error("Conge or Employer not found");
        }
        return conge;
    }

    private void envoyerEmail(String destinataire, String sujet, String corps) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("", ""); // Remplacez par vos identifiants de messagerie
            }
        });
        try {

            // Création du message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("")); // Remplacez par votre adresse e-mail
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinataire));
            message.setSubject(sujet);
            message.setText(corps);
            // Envoi du message
            Transport.send(message);
            System.out.println("E-mail envoyé avec succès");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}


