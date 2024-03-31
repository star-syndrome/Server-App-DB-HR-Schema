package org.metrodataacademy.TugasSpringBoot.repositories;

import org.metrodataacademy.TugasSpringBoot.models.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {

    Boolean existsByCode(String code);

    @Query("SELECT c FROM Country c WHERE c.name LIKE %:name%")
    List<Country> searchByName(String name);
}