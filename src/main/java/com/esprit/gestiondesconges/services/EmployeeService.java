package com.esprit.gestiondesconges.services;

import com.esprit.gestiondesconges.entities.*;
import com.esprit.gestiondesconges.entities.Role;
import com.esprit.gestiondesconges.repositories.EmployeeRepo;
import com.esprit.gestiondesconges.repositories.HistoriqueRepo;
import com.esprit.gestiondesconges.services.interfaces.IemployeeService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Service

public class EmployeeService implements IemployeeService {

    private final EmployeeRepo employeeRepository;
    private final HistoriqueRepo historiqueRepo;
    private final PasswordEncoder passwordEncoder;
    private final EmployeeRepo repository;
    @Override
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployee(Long idEmployee, Employee employeeDetails) {
        Optional<Employee> existingEmployee = employeeRepository.findById(idEmployee);
        if (existingEmployee.isPresent()) {
            //Employee updatedEmployee = existingEmployee.get();

            log.info("Updating employee: " + employeeDetails.toString());
            return employeeRepository.save(employeeDetails);
        } else {
            throw new RuntimeException("Employee not found, cannot update.");
        }
    }



    @Override
    public void deleteEmployee(Long idEmployee) {
        if (employeeRepository.existsById(idEmployee)) {
            employeeRepository.deleteById(idEmployee);
            log.info("Deleted employee with id: " + idEmployee);
        } else {
            throw new RuntimeException("Employee not found, cannot delete.");
        }
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> getEmployeeById(Long idEmployee) {
        return employeeRepository.findById(idEmployee);
    }

    @Override
    public Optional<Employee> getEmployeeByName(String nom) {
        return employeeRepository.findByNom(nom);
    }

    @Override
    public Optional<Employee> getEmployeeByNameAndPrenom(String nom, String prenom) {
        return employeeRepository.findByNomAndPrenom(nom, prenom);
    }
    @Override
    public Optional <Employee> getEmployeeByPoste(Role role) {
        return employeeRepository.findEmployeeByRole(role);
    }
    @Override
    public void affecterHistoriqueAEmployee(long idHistorique, long idEmployee) {
        Employee employee = employeeRepository.findById(idEmployee)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        Historique historique = historiqueRepo.findById(idHistorique)
                .orElseThrow(() -> new RuntimeException("Historique not found"));

        historique.setEmployee(employee);
        historiqueRepo.save(historique);
    }
    @Scheduled(cron = "0 0 0 1 * *")
    public void ajouterCongesTousEmployes() {
        List<Employee> employees = employeeRepository.findAll();
        for (Employee employee : employees) {
            double nouveauSolde = employee.getSoldeConge() + 1.8;
            employee.setSoldeConge((int) nouveauSolde);

            employeeRepository.save(employee);
        }
    }

    public List<Employee> findByRole(Role role) {
        return employeeRepository.findByRole(role);
    }

    public List<Employee> getEmployeesWithoutTeam() {
        return employeeRepository.findAllByEquipeIsNull();
    }

    public List<Employee> getManagersWithoutTeam() {
        return employeeRepository.findAllByEquipeIsNullAndRole(Type.Leader);
    }

    public List<Employee> getMembersWithoutTeam() {
        return employeeRepository.findAllByEquipeIsNullAndRole(Type.Member);
    }

    @Transactional
    public void desaffecterEmployeesByEquipeId(Long idEquipe) {
        employeeRepository.desaffecterEmployeesByEquipeId(idEquipe);
    }



    public void changePassword(String email , ChangePasswordRequest request) {

        // Retrieve user from repository
        Employee employee = repository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        // check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), employee.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        // check if the two new passwords are the same
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        // update the password
        employee.setPassword(passwordEncoder.encode(request.getNewPassword()));
        // Save the updated user
        repository.save(employee);
    }
}
