package com.esprit.gestiondesconges.restControllers;

import com.esprit.gestiondesconges.entities.Employee;
import com.esprit.gestiondesconges.services.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{idEmployee}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long idEmployee) {
        Employee employee = employeeService.getEmployeeById(idEmployee)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        return ResponseEntity.ok().body(employee);
    }

    @GetMapping("/search")
    public ResponseEntity<Employee> getEmployeeByName(@RequestParam String nom) {
        Employee employee = employeeService.getEmployeeByName(nom)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        return ResponseEntity.ok().body(employee);
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee createdEmployee = employeeService.createEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
    }

    @PutMapping("/{idEmployee}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long idEmployee,
                                                   @RequestBody Employee employeeDetails) {
        Employee updatedEmployee = employeeService.updateEmployee(idEmployee, employeeDetails);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/{idEmployee}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long idEmployee) {
        employeeService.deleteEmployee(idEmployee);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{idEmployee}/historique/{idHistorique}")
    public ResponseEntity<String> affecterHistoriqueAEmployee(
            @PathVariable long idHistorique,
            @PathVariable long idEmployee) {
        try {
            employeeService.affecterHistoriqueAEmployee(idHistorique, idEmployee);
            return ResponseEntity.ok("Historique affecté avec succès à l'employé.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Historique ou employé non trouvé.");
        }
    }
}

