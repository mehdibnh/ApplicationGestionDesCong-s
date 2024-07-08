package com.esprit.gestiondesconges.repositories;

import com.esprit.gestiondesconges.entities.Employee;
import com.esprit.gestiondesconges.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {
    Optional<Employee> findByNom(String nom);
    Optional<Employee> findById(Long aLong);

    Optional<Employee> findByNomAndPrenom(String nom, String prenom);

  Optional <Employee> findEmployeeByRole (Role role);
}
