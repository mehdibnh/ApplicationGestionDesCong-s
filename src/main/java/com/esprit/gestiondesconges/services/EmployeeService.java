package com.esprit.gestiondesconges.services;

import com.esprit.gestiondesconges.entities.Employee;
import com.esprit.gestiondesconges.repositories.EmployeeRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Service
public class EmployeeService {

    private final EmployeeRepo employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public Optional<Employee> getEmployeeByName(String nom) {
        return employeeRepository.findByNom(nom);
    }

    public Employee createEmployee(Employee employee) {
        log.info("Creating employee: " + employee.toString());
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long id, Employee employeeDetails) {
        if (employeeRepository.existsById(id)) {
            employeeDetails.setIdemployee(id);
            log.info("Updating employee: " + employeeDetails.toString());
            return employeeRepository.save(employeeDetails);
        } else {
            throw new RuntimeException("Employee not found, cannot update.");
        }
    }

    public void deleteEmployee(Long id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            log.info("Deleted employee with id: " + id);
        } else {
            throw new RuntimeException("Employee not found, cannot delete.");
        }
    }
}
