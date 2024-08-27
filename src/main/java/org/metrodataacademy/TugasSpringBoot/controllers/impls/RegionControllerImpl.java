package org.metrodataacademy.TugasSpringBoot.controllers.impls;

import org.metrodataacademy.TugasSpringBoot.controllers.GenericController;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.CreateRegionRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.UpdateRegionRequest;
import org.metrodataacademy.TugasSpringBoot.services.impls.RegionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/region")
@PreAuthorize(value = "hasAnyRole('ADMIN', 'USER')")
public class RegionControllerImpl implements
        GenericController<Object, Integer, String, CreateRegionRequest, UpdateRegionRequest> {

    @Autowired
    private RegionServiceImpl regionService;

    @Override
    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(value = "hasAnyAuthority('READ_ADMIN', 'READ_USER')")
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok().body(regionService.getAll());
    }

    @Override
    @GetMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(value = "hasAnyAuthority('READ_ADMIN', 'READ_USER')")
    public ResponseEntity<Object> getById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(regionService.getById(id));
    }

    @Override
    @GetMapping(
            path = "/search",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(value = "hasAnyAuthority('READ_ADMIN', 'READ_USER')")
    public ResponseEntity<Object> search(@RequestParam String name) {
        return ResponseEntity.ok().body(regionService.search(name));
    }

    @Override
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(value = "hasAnyAuthority('CREATE_USER', 'CREATE_ADMIN')")
    public ResponseEntity<Object> create(@Validated @RequestBody CreateRegionRequest request) {
        return ResponseEntity.ok().body(regionService.create(request));
    }

    @Override
    @PutMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(value = "hasAnyAuthority('UPDATE_USER', 'UPDATE_ADMIN')")
    public ResponseEntity<Object> update(@PathVariable Integer id,
                                         @Validated @RequestBody UpdateRegionRequest request) {
        return ResponseEntity.ok().body(regionService.update(id, request));
    }

    @Override
    @DeleteMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(value = "hasAnyAuthority('DELETE_USER', 'DELETE_ADMIN')")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        return ResponseEntity.ok().body(regionService.delete(id));
    }
}