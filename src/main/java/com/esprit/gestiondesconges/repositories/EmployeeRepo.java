package com.esprit.gestiondesconges.repositories;

import com.esprit.gestiondesconges.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {
    Optional<Employee> findByNom(String nom);
    Optional<Employee> findById(Long aLong);

}
