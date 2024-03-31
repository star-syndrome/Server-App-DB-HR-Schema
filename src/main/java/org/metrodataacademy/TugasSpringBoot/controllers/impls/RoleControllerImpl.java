package org.metrodataacademy.TugasSpringBoot.controllers.impls;

import org.metrodataacademy.TugasSpringBoot.controllers.GenericController;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.CreateRoleRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.UpdateRoleRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.responses.ResponseData;
import org.metrodataacademy.TugasSpringBoot.services.impls.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
@PreAuthorize(value = "hasRole('ADMIN')")
public class RoleControllerImpl implements
        GenericController<Object, Integer, String, CreateRoleRequest, UpdateRoleRequest> {

    @Autowired
    private RoleServiceImpl roleService;

    @Override
    @GetMapping(
            path = "/getAll",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(value = "hasAuthority('READ_ADMIN')")
    public ResponseEntity<Object> getAll() {
        return ResponseData.statusResponse(roleService.getAll(),
                HttpStatus.OK, "Successfully getting all roles!");
    }

    @Override
    @GetMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(value = "hasAuthority('READ_ADMIN')")
    public ResponseEntity<Object> getById(@PathVariable Integer id) {
        return ResponseData.statusResponse(roleService.getById(id),
                HttpStatus.OK, "Successfully getting data role with id " + id + "!");
    }

    @Override
    @GetMapping(
            path = "/search",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(value = "hasAuthority('READ_ADMIN')")
    public ResponseEntity<Object> search(@RequestParam String name) {
        return ResponseData.statusResponse(roleService.search(name),
                HttpStatus.OK, "Successfully get data roles by method searching!");
    }

    @Override
    @PostMapping(
            path = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(value = "hasAuthority('CREATE_ADMIN')")
    public ResponseEntity<Object> create(@Validated @RequestBody CreateRoleRequest request) {
        return ResponseData.statusResponse(roleService.create(request),
                HttpStatus.OK, "Successfully created a new role!");
    }

    @Override
    @PutMapping(
            path = "/update/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(value = "hasAuthority('UPDATE_ADMIN')")
    public ResponseEntity<Object> update(@PathVariable Integer id,
                                         @RequestBody UpdateRoleRequest request) {
        return ResponseData.statusResponse(roleService.update(id, request),
                HttpStatus.OK, "Successfully updated a role!");
    }

    @Override
    @DeleteMapping(
            path = "/delete/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(value = "hasAuthority('DELETE_ADMIN')")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        roleService.delete(id);
        return ResponseData.statusResponse(null, HttpStatus.OK, "Successfully deleted a role!");
    }
}