package org.metrodataacademy.TugasSpringBoot.repositories;

import org.metrodataacademy.TugasSpringBoot.models.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Boolean existsByPhoneOrEmail(String phone, String email);

    @Query("SELECT e FROM Employee e WHERE e.name LIKE %:name%")
    List<Employee> searchByName(String name);

    @Query("SELECT COUNT(e) > 0 FROM Employee e WHERE e.email= :email AND e.id != :id")
    Boolean countByEmailForUpdate(String email, Integer id);

    @Query("SELECT COUNT(e) > 0 FROM Employee e WHERE e.phone = :phone AND e.id != :id")
    Boolean countByPhoneForUpdate(String phone, Integer id);

    Optional<Employee> findByUserUsername(String username);
}