package com.esprit.gestiondesconges.repositories;

import com.esprit.gestiondesconges.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeRepo extends JpaRepository<Employee,Long> {

    Optional<Employee> findByEmail(String email);
}