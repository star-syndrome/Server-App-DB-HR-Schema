package org.metrodataacademy.TugasSpringBoot.repositories;

import org.metrodataacademy.TugasSpringBoot.models.entities.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Integer> {

    Optional<Privilege> findByName(String name);

    Boolean existsByName(String name);

    @Query("SELECT p FROM Privilege p WHERE p.name LIKE %:name%")
    List<Privilege> searchPrivilegeByName(String name);
}