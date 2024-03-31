package org.metrodataacademy.TugasSpringBoot.controllers.impls;

import org.metrodataacademy.TugasSpringBoot.controllers.GenericController;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.UpdateEmployeeRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.responses.ResponseData;
import org.metrodataacademy.TugasSpringBoot.models.entities.Employee;
import org.metrodataacademy.TugasSpringBoot.services.impls.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
@PreAuthorize(value = "hasAnyRole('ADMIN', 'USER')")
public class EmployeeControllerImpl implements
        GenericController<Object, Integer, String, Employee, UpdateEmployeeRequest> {

    @Autowired
    private EmployeeServiceImpl employeeService;

    @Override
    @GetMapping(
            path = "/getAll",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(value = "hasAuthority('READ_ADMIN')")
    public ResponseEntity<Object> getAll() {
        return ResponseData.statusResponse(employeeService.getAll(),
                HttpStatus.OK, "Successfully getting all employees!");
    }

    @Override
    @GetMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(value = "hasAnyAuthority('READ_ADMIN', 'READ_USER')")
    public ResponseEntity<Object> getById(@PathVariable Integer id) {
        return ResponseData.statusResponse(employeeService.getById(id),
                HttpStatus.OK, "Successfully getting data employee with id " + id + "!");
    }

    @Override
    @GetMapping(
            path = "/search",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(value = "hasAuthority('READ_ADMIN')")
    public ResponseEntity<Object> search(@RequestParam String name) {
        return ResponseData.statusResponse(employeeService.search(name),
                HttpStatus.OK, "Successfully get data employees by method searching!");
    }

    @Override
    @PutMapping(
            path = "/update/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(value = "hasAuthority('UPDATE_USER')")
    public ResponseEntity<Object> update(@PathVariable Integer id,
                                         @Validated @RequestBody UpdateEmployeeRequest req) {
        return ResponseData.statusResponse(employeeService.update(id, req),
                HttpStatus.OK, "Successfully updated an employee!");
    }

    @Override
    @DeleteMapping(
            path = "/delete/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(value = "hasAuthority('DELETE_USER')")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        employeeService.delete(id);
        return ResponseData.statusResponse(null, HttpStatus.OK, "Successfully delete an employee!");
    }

    @Override
    public ResponseEntity<Object> create(Employee req) {
        return null;
    }
}