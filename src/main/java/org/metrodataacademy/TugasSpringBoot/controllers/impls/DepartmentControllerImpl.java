package org.metrodataacademy.TugasSpringBoot.controllers.impls;

import org.metrodataacademy.TugasSpringBoot.controllers.GenericController;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.CreateDepartmentRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.UpdateDepartmentRequest;
import org.metrodataacademy.TugasSpringBoot.services.impls.DepartmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/department")
@PreAuthorize(value = "hasRole('ADMIN')")
public class DepartmentControllerImpl implements
        GenericController<Object, Integer, String, CreateDepartmentRequest, UpdateDepartmentRequest> {

    @Autowired
    private DepartmentServiceImpl departmentService;

    @Override
    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(value = "hasAuthority('READ_ADMIN')")
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok().body(departmentService.getAll());
    }

    @Override
    @GetMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(value = "hasAuthority('READ_ADMIN')")
    public ResponseEntity<Object> getById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(departmentService.getById(id));
    }

    @Override
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(value = "hasAuthority('CREATE_ADMIN')")
    public ResponseEntity<Object> create(@Validated @RequestBody CreateDepartmentRequest request) {
        return ResponseEntity.ok().body(departmentService.create(request));
    }

    @Override
    @PutMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(value = "hasAuthority('UPDATE_ADMIN')")
    public ResponseEntity<Object> update(@PathVariable Integer id,
                                         @Validated @RequestBody UpdateDepartmentRequest request) {
        return ResponseEntity.ok().body(departmentService.update(id, request));
    }

    @Override
    @DeleteMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(value = "hasAuthority('DELETE_ADMIN')")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        return ResponseEntity.ok().body(departmentService.delete(id));
    }

    // Unused Endpoint
    @Override
    public ResponseEntity<Object> search(@RequestParam String name) {
        return null;
    }
}