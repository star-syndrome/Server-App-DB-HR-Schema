package org.metrodataacademy.TugasSpringBoot.controllers.impls;

import org.metrodataacademy.TugasSpringBoot.controllers.GenericController;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.CreateRoleRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.UpdateRoleRequest;
import org.metrodataacademy.TugasSpringBoot.services.impls.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
public class RoleControllerImpl implements
        GenericController<Object, Integer, String, CreateRoleRequest, UpdateRoleRequest> {

    @Autowired
    private RoleServiceImpl roleService;

    @Override
    @GetMapping(
            path = "/getAll",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok().body(roleService.getAll());
    }

    @Override
    @GetMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> getById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(roleService.getById(id));
    }

    @Override
    @GetMapping(
            path = "/search",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> search(@RequestParam String name) {
        return ResponseEntity.ok().body(roleService.search(name));
    }

    @Override
    @PostMapping(
            path = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> create(@Validated @RequestBody CreateRoleRequest request) {
        return ResponseEntity.ok().body(roleService.create(request));
    }

    @Override
    @PutMapping(
            path = "/update/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> update(@PathVariable Integer id,
                                         @RequestBody UpdateRoleRequest request) {
        return ResponseEntity.ok().body(roleService.update(id, request));
    }

    @Override
    @DeleteMapping(
            path = "/delete/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        return ResponseEntity.ok().body(roleService.delete(id));
    }
}