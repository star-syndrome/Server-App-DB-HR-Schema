package org.metrodataacademy.TugasSpringBoot.controllers.impls;

import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.CreateJobRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.UpdateJobRequest;
import org.metrodataacademy.TugasSpringBoot.services.impls.JobServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/job")
@PreAuthorize(value = "hasRole('ADMIN')")
public class JobController {

    @Autowired
    private JobServiceImpl jobService;

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(value = "hasAuthority('READ_ADMIN')")
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok().body(jobService.getAll());
    }

    @GetMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(value = "hasAuthority('READ_ADMIN')")
    public ResponseEntity<Object> getById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(jobService.getById(id));
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(value = "hasAuthority('CREATE_ADMIN')")
    public ResponseEntity<Object> create(@Validated @RequestBody CreateJobRequest request) {
        return ResponseEntity.ok().body(jobService.create(request));
    }

    @PutMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(value = "hasAuthority('UPDATE_ADMIN')")
    public ResponseEntity<Object> update(@PathVariable Integer id,
                                         @Validated @RequestBody UpdateJobRequest request) {
        return ResponseEntity.ok().body(jobService.update(id, request));
    }

    @DeleteMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(value = "hasAuthority('DELETE_ADMIN')")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        return ResponseEntity.ok().body(jobService.delete(id));
    }
}