package org.metrodataacademy.TugasSpringBoot.repositories;

import org.metrodataacademy.TugasSpringBoot.models.entities.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job, String> {

    Optional<Job> findById(Integer id);

    @Query("SELECT COUNT(j) > 0 FROM Job j WHERE j.code = :code AND j.id != :id")
    Boolean countByCodeForUpdate(String code, Integer id);

    boolean existsById(Integer id);
}