package org.metrodataacademy.TugasSpringBoot.repositories;

import org.metrodataacademy.TugasSpringBoot.models.entities.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job, String> {

    Optional<Job> findById(String id);
}