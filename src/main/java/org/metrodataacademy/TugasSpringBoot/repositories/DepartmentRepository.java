package org.metrodataacademy.TugasSpringBoot.repositories;

import org.metrodataacademy.TugasSpringBoot.models.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    Optional<Department> findById(Integer Id);
}