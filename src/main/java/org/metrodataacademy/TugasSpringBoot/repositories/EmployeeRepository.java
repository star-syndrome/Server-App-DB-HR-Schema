package org.metrodataacademy.TugasSpringBoot.repositories;

import org.metrodataacademy.TugasSpringBoot.models.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Boolean existsByPhoneOrEmail(String phone, String email);

    @Query("SELECT e FROM Employee e WHERE e.name LIKE %:name%")
    List<Employee> searchByName(String name);
}