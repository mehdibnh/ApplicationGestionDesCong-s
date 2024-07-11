package com.esprit.gestiondesconges.repositories;

import com.esprit.gestiondesconges.entities.Employee;
import com.esprit.gestiondesconges.entities.Role;
import com.esprit.gestiondesconges.entities.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {
    Optional<Employee> findByNom(String nom);

    Optional<Employee> findByEmail(String email);
    Optional<Employee> findById(Long aLong);

    Optional<Employee> findByNomAndPrenom(String nom, String prenom);

  Optional <Employee> findEmployeeByRole (Role role);
    @Query("SELECT e FROM Employee e WHERE e.role = :role")
    List<Employee> findByRole(Role role);

    @Query("SELECT e FROM Employee e WHERE e.equipe IS NULL")
    List<Employee> findAllByEquipeIsNull();
    @Query("SELECT e FROM Employee e WHERE e.equipe IS NULL AND e.role = :role")
    List<Employee> findAllByEquipeIsNullAndRole( Type role);

    @Modifying
    @Query("UPDATE Employee e SET e.equipe = null WHERE e.equipe.idEquipe = :idEquipe")
    void desaffecterEmployeesByEquipeId(Long idEquipe);
}
