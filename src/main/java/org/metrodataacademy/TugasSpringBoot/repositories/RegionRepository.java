package org.metrodataacademy.TugasSpringBoot.repositories;

import org.metrodataacademy.TugasSpringBoot.models.entities.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RegionRepository extends JpaRepository<Region, Integer> {

    Boolean existsByName(String name);

    @Query("SELECT r FROM Region r WHERE r.name LIKE %:name%")
    List<Region> searchRegionByName(String name);
}