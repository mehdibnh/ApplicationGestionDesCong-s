package com.esprit.gestiondesconges.services.interfaces;

import com.esprit.gestiondesconges.entities.Employee;
import java.util.List;
import java.util.Optional;

public interface IemployeeService {
    Employee createEmployee(Employee employee);
    Employee updateEmployee(Long idEmployee, Employee employeeDetails);
    void deleteEmployee(Long idEmployee);
    List<Employee> getAllEmployees();
    Optional<Employee> getEmployeeById(Long idEmployee);
    Optional<Employee> getEmployeeByName(String nom);
    void affecterHistoriqueAEmployee(long idHistorique, long idEmployee);
}

