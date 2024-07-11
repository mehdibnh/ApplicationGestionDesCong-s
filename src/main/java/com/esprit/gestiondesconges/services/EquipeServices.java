package com.esprit.gestiondesconges.services;
import com.esprit.gestiondesconges.EmailService;
import com.esprit.gestiondesconges.entities.Employee;
import com.esprit.gestiondesconges.entities.Equipe;
import com.esprit.gestiondesconges.entities.Role;
import com.esprit.gestiondesconges.repositories.IEmployerRepo;
import com.esprit.gestiondesconges.repositories.IEquipe;
import com.esprit.gestiondesconges.services.interfaces.IEquipeServices;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Slf4j
@Service

public class EquipeServices implements IEquipeServices {

    IEquipe equipeRepo;
    IEmployerRepo employeeRepo ;
    EmailService emailService ;


    @Override
    public Equipe ajouterEquipe(Equipe equipe) {
        return equipeRepo.save(equipe);
    }

    @Override
    public Equipe supprimerEquipe(Long idEquipe) {
        Equipe equipe = recupererEquipe(idEquipe);
        if (equipe != null) {
            equipeRepo.delete(equipe);
        }
        return equipe;
    }

    @Override
    public Equipe recupererEquipe(Long idEquipe) {
        return equipeRepo.findById(idEquipe).orElse(null);
    }

    @Override
    public Equipe modifierEquipe(Long idEquipe, Equipe equipe) {
        if (equipeRepo.existsById(idEquipe)) {
            equipe.setIdEquipe(idEquipe);
            return equipeRepo.save(equipe);
        }
        return null;
    }

    @Override
    public List<Equipe> recupererListeEquipe() {
        return equipeRepo.findAll();
    };

    @Transactional
    @Override
    public String assignTeamLeader(Long equipeId, Long employeeId) {
        Equipe equipe = equipeRepo.findById(equipeId)
                .orElseThrow(() -> new RuntimeException("Equipe not found"));
        Employee employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // Set the team leader in Equipe
        equipe.setTeamLeader(employee);
        equipeRepo.save(equipe);

        // Optionally, set the Equipe for the employee
        employee.setEquipe(equipe);
        employeeRepo.save(employee);

        return "Team leader assigned successfully";
    }



    public String addMembersToEquipe(Long equipeId, List<Long> employeeIds) {
        Equipe equipe = equipeRepo.findById(equipeId)
                .orElseThrow(() -> new RuntimeException("Equipe not found"));

        Set<Employee> currentMembers = equipe.getEmployees();
        int currentMemberCount = currentMembers.size();
        int additionalMemberCount = employeeIds.size();

        if (currentMemberCount + additionalMemberCount > equipe.getNombrePersonnes()) {
            throw new RuntimeException("Cannot add members: team size will exceed the limit");
        }

        for (Long employeeId : employeeIds) {
            Employee employee = employeeRepo.findById(employeeId)
                    .orElseThrow(() -> new RuntimeException("Employee not found"));
            employee.setEquipe(equipe);
            employeeRepo.save(employee);
        }

        return "Members added successfully";
    }

    @Transactional
    public void deleteEquipe(Long equipeId) {
        Optional<Equipe> equipeOptional = equipeRepo.findById(equipeId);
        equipeOptional.ifPresent(equipe -> {
            // Dissocier les employés de l'équipe
            for (Employee employee : equipe.getEmployees()) {
                employee.setEquipe(null); // Dissocier l'employé de l'équipe
            }
            equipeRepo.delete(equipe); // Supprimer l'équipe
        });
    }
    public Equipe updateEquipe(Long idEquipe, Equipe equipeDetails) {
        Equipe equipe = equipeRepo.findById(idEquipe).orElseThrow(() -> new RuntimeException("Equipe not found"));

        equipe.setNomEquipe(equipeDetails.getNomEquipe());
        equipe.setNombrePersonnes(equipeDetails.getNombrePersonnes());
        equipe.setProjet(equipeDetails.getProjet());
        equipe.setDepartement(equipeDetails.getDepartement());

        return equipeRepo.save(equipe);
    }
    @Override
    public void sendTeamLeaderNotification(Long equipeId, Long employeeId) {
        Equipe equipe = equipeRepo.findById(equipeId)
                .orElseThrow(() -> new RuntimeException("Equipe not found"));
        Employee teamLeader = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Team leader not found"));

        String subject = "You have been assigned as a team leader";
        String message = String.format("Dear %s %s,\n\nYou have been assigned as the team leader of team %s.",
                teamLeader.getPrenom(), teamLeader.getNom(), equipe.getNomEquipe());

        emailService.sendEmail(teamLeader.getEmail(), subject, message); // Implement sendEmail in your EmailService
    }

     }

