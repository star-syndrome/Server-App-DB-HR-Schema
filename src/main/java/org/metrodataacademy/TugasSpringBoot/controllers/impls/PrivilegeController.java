package org.metrodataacademy.TugasSpringBoot.controllers.impls;

import org.metrodataacademy.TugasSpringBoot.controllers.GenericController;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.CreatePrivilegeRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.UpdatePrivilegeRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.responses.ResponseData;
import org.metrodataacademy.TugasSpringBoot.services.impls.PrivilegeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/privilege")
@PreAuthorize(value = "hasRole('ADMIN')")
public class PrivilegeController implements
        GenericController<Object, Integer, String, CreatePrivilegeRequest, UpdatePrivilegeRequest> {

    @Autowired
    private PrivilegeServiceImpl privilegeService;

    @Override
    @GetMapping(
            path = "/getAll",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(value = "hasAuthority('READ_ADMIN')")
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok()
                .body(ResponseData.statusResponse(privilegeService.getAll(),
                        HttpStatus.OK, "Successfully getting all privileges!"));
    }

    @Override
    @GetMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(value = "hasAuthority('READ_ADMIN')")
    public ResponseEntity<Object> getById(@PathVariable Integer id) {
        return ResponseEntity.ok()
                .body(ResponseData.statusResponse(privilegeService.getById(id),
                        HttpStatus.OK, "Successfully getting data privilege with id " + id + "!"));
    }

    @Override
    @GetMapping(
            path = "/search",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(value = "hasAuthority('READ_ADMIN')")
    public ResponseEntity<Object> search(@RequestParam String name) {
        return ResponseEntity.ok()
                .body(ResponseData.statusResponse(privilegeService.search(name),
                        HttpStatus.OK, "Successfully get data privileges by method searching!"));
    }

    @Override
    @PostMapping(
            path = "/create",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(value = "hasAuthority('CREATE_ADMIN')")
    public ResponseEntity<Object> create(@Validated @RequestBody CreatePrivilegeRequest request) {
        return ResponseEntity.ok()
                .body(ResponseData.statusResponse(privilegeService.create(request),
                        HttpStatus.OK, "Successfully created a new privilege"));
    }

    @Override
    @PutMapping(
            path = "/update/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(value = "hasAuthority('UPDATE_ADMIN')")
    public ResponseEntity<Object> update(@PathVariable Integer id,
                                         @Validated @RequestBody UpdatePrivilegeRequest request) {
        return ResponseEntity.ok()
                .body(ResponseData.statusResponse(privilegeService.update(id, request),
                        HttpStatus.OK, "Successfully updated a privilege"));
    }

    @Override
    @DeleteMapping(
            path = "/delete/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(value = "hasAuthority('DELETE_ADMIN')")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        privilegeService.delete(id);
        return ResponseEntity.ok()
                .body(ResponseData.statusResponse(null, HttpStatus.OK,
                        "Successfully deleted a privilege!"));
    }
}