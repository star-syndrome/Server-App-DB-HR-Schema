package org.metrodataacademy.TugasSpringBoot.services.impls;

import lombok.extern.slf4j.Slf4j;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.CreateJobRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.UpdateJobRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.responses.JobResponse;
import org.metrodataacademy.TugasSpringBoot.models.entities.Job;
import org.metrodataacademy.TugasSpringBoot.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class JobServiceImpl {

    @Autowired
    private JobRepository jobRepository;

    @Transactional(readOnly = true)
    public List<JobResponse> getAll() {
        log.info("Successfully getting all jobs!");
        return jobRepository.findAll().stream()
                .map(this::toJobResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public JobResponse getById(String id) {
        log.info("Getting job data from job id {}", id);
        return jobRepository.findById(id)
                .map(this::toJobResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found!"));
    }

    public JobResponse create(CreateJobRequest req) {
        log.info("Trying to add a new job");

        Job job = Job.builder()
                .id(req.getId())
                .title(req.getTitle())
                .minSalary(req.getMinSalary())
                .maxSalary(req.getMaxSalary())
                .build();
        jobRepository.save(job);
        log.info("Adding new job was successful!");

        return toJobResponse(job);
    }

    public JobResponse update(String id, UpdateJobRequest req) {
        log.info("Trying to update job data with id: {}", id);

        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found!"));

        job.setTitle(req.getTitle());
        job.setMinSalary(req.getMinSalary());
        job.setMaxSalary(req.getMaxSalary());
        jobRepository.save(job);
        log.info("Updating job was successful!");

        return toJobResponse(job);
    }

    public JobResponse delete(String id) {
        log.info("Trying to delete job with id: {}", id);
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found!"));

        jobRepository.delete(job);
        log.info("Deleting job with id: {} was successful!", id);

        return toJobResponse(job);
    }

    public JobResponse toJobResponse(Job job) {
        return JobResponse.builder()
                .id(job.getId())
                .title(job.getTitle())
                .minSalary(job.getMinSalary())
                .maxSalary(job.getMaxSalary())
                .build();
    }
}