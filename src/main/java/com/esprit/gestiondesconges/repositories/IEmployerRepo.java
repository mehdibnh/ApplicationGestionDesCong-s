package com.esprit.gestiondesconges.repositories;

import com.esprit.gestiondesconges.entities.Conge;
import com.esprit.gestiondesconges.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEmployerRepo extends JpaRepository<Employee,Long> {
}
