package org.metrodataacademy.TugasSpringBoot.controllers.impls;

import org.metrodataacademy.TugasSpringBoot.controllers.GenericController;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.CreatePrivilegeRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.UpdatePrivilegeRequest;
import org.metrodataacademy.TugasSpringBoot.services.impls.PrivilegeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/privilege")
public class PrivilegeControllerImpl implements
        GenericController<Object, Integer, String, CreatePrivilegeRequest, UpdatePrivilegeRequest> {

    @Autowired
    private PrivilegeServiceImpl privilegeService;

    @Override
    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok().body(privilegeService.getAll());
    }

    @Override
    @GetMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> getById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(privilegeService.getById(id));
    }

    @Override
    @GetMapping(
            path = "/search",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> search(@RequestParam String name) {
        return ResponseEntity.ok().body(privilegeService.search(name));
    }

    @Override
    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> create(@Validated @RequestBody CreatePrivilegeRequest request) {
        return ResponseEntity.ok().body(privilegeService.create(request));
    }

    @Override
    @PutMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> update(@PathVariable Integer id,
                                         @Validated @RequestBody UpdatePrivilegeRequest request) {
        return ResponseEntity.ok().body(privilegeService.update(id, request));
    }

    @Override
    @DeleteMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        return ResponseEntity.ok().body(privilegeService.delete(id));
    }
}