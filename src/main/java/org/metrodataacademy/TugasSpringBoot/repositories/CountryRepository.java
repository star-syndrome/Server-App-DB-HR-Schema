package org.metrodataacademy.TugasSpringBoot.repositories;

import org.metrodataacademy.TugasSpringBoot.models.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {

    Boolean existsByCode(String code);

    Boolean existsByName(String name);

    @Query("SELECT c FROM Country c WHERE c.name LIKE %:name%")
    List<Country> searchByName(String name);

    @Query("SELECT COUNT(c) > 0 FROM Country c WHERE c.name = :name AND c.id != :id")
    Boolean countByNameForUpdate(String name, Integer id);

    @Query("SELECT COUNT(c) > 0 FROM Country c WHERE c.code = :code AND c.id != :id")
    Boolean countByCodeForUpdate(String code, Integer id);
}