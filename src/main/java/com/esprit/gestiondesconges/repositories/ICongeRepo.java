package com.esprit.gestiondesconges.repositories;

import com.esprit.gestiondesconges.entities.Conge;
import com.esprit.gestiondesconges.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICongeRepo extends JpaRepository<Conge,Long> {

    List<Conge> getAllByEmployee(Employee employee);


}
