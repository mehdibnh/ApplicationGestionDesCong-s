package com.esprit.gestiondesconges.services;
import com.esprit.gestiondesconges.entities.Employee;
import org.springframework.stereotype.Service;
import com.esprit.gestiondesconges.entities.Employee;
import com.esprit.gestiondesconges.entities.Historique;

import java.util.List;
import java.util.Optional;
import java.util.List;

public interface IemployeeService {

    Employee ajouteremployee(Employee employee);
    Employee modifieremployee(Employee employee);
    void supprimeremployee(Long id);
    Employee chercheremployee(Long id);
    List<Employee> listeremployees();
    Employee affecteremployeeAHistorique(String nomemployee, Historique historique);

    }
