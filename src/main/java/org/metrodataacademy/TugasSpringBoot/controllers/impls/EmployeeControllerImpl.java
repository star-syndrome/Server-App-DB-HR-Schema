package org.metrodataacademy.TugasSpringBoot.controllers.impls;

import org.metrodataacademy.TugasSpringBoot.controllers.GenericController;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.CreateEmployeeRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.UpdateEmployeeRequest;
import org.metrodataacademy.TugasSpringBoot.services.impls.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
@PreAuthorize(value = "hasRole('ADMIN')")
public class EmployeeControllerImpl implements
        GenericController<Object, Integer, String, CreateEmployeeRequest, UpdateEmployeeRequest> {

    @Autowired
    private EmployeeServiceImpl employeeService;

    @Override
    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(value = "hasAuthority('READ_ADMIN')")
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok().body(employeeService.getAll());
    }

    @GetMapping(
            path = "/manager",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(value = "hasAuthority('READ_ADMIN')")
    public ResponseEntity<Object> getAllManager() {
        return ResponseEntity.ok().body(employeeService.getAllManager());
    }

    @Override
    @GetMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(value = "hasAuthority('READ_ADMIN')")
    public ResponseEntity<Object> getById(@PathVariable Integer id) {
        return ResponseEntity.ok()
                .body(employeeService.getById(id));
    }

    @Override
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(value = "hasAuthority('CREATE_ADMIN')")
    public ResponseEntity<Object> create(@RequestBody @Validated CreateEmployeeRequest req) {
        return ResponseEntity.ok().body(employeeService.create(req));
    }

    @Override
    @PutMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(value = "hasAuthority('UPDATE_ADMIN')")
    public ResponseEntity<Object> update(@PathVariable Integer id,
                                         @Validated @RequestBody UpdateEmployeeRequest req) {
        return ResponseEntity.ok().body(employeeService.update(id, req));
    }

    @Override
    @DeleteMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(value = "hasAuthority('DELETE_ADMIN')")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        return ResponseEntity.ok().body(employeeService.delete(id));
    }

    @Override
    public ResponseEntity<Object> search(@RequestParam String name) {
        return null;
    }
}