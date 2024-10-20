package org.metrodataacademy.TugasSpringBoot.repositories;

import org.metrodataacademy.TugasSpringBoot.models.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Boolean existsByEmail(String email);

    @Query("SELECT COUNT(e) > 0 FROM Employee e WHERE e.email= :email AND e.id != :id")
    Boolean countByEmailForUpdate(String email, Integer id);

    Optional<Employee> findByUserUsername(String username);

    @Query("SELECT DISTINCT e FROM Employee e WHERE e.id IN " +
            "(SELECT DISTINCT te.manager FROM Employee te WHERE te.manager IS NOT NULL)")
    List<Employee> findAllManager();
}