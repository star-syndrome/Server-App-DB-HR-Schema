package org.metrodataacademy.TugasSpringBoot.controllers.impls;

import org.metrodataacademy.TugasSpringBoot.controllers.GenericController;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.CreateRegionRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.UpdateRegionRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.responses.ResponseData;
import org.metrodataacademy.TugasSpringBoot.services.impls.RegionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
            path = "/getAll",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(value = "hasAnyAuthority('READ_ADMIN', 'READ_USER')")
    public ResponseEntity<Object> getAll() {
        return ResponseData.statusResponse(regionService.getAll(),
                HttpStatus.OK, "Successfully getting all regions!");
    }

    @Override
    @GetMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(value = "hasAnyAuthority('READ_ADMIN', 'READ_USER')")
    public ResponseEntity<Object> getById(@PathVariable Integer id) {
        return ResponseData.statusResponse(regionService.getById(id),
                HttpStatus.OK, "Successfully getting data region with id " + id + "!");
    }

    @Override
    @GetMapping(
            path = "/search",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(value = "hasAnyAuthority('READ_ADMIN', 'READ_USER')")
    public ResponseEntity<Object> search(@RequestParam String name) {
        return ResponseData.statusResponse(regionService.search(name),
                HttpStatus.OK, "Successfully get data regions by method searching!");
    }

    @Override
    @PostMapping(
            path = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(value = "hasAuthority('CREATE_ADMIN')")
    public ResponseEntity<Object> create(@Validated @RequestBody CreateRegionRequest request) {
        return ResponseData.statusResponse(regionService.create(request),
                HttpStatus.OK, "Successfully created a new region!");
    }

    @Override
    @PutMapping(
            path = "/update/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(value = "hasAuthority('UPDATE_ADMIN')")
    public ResponseEntity<Object> update(@PathVariable Integer id,
                                         @Validated @RequestBody UpdateRegionRequest request) {
        return ResponseData.statusResponse(regionService.update(id, request),
                HttpStatus.OK, "Successfully updated a region!");
    }

    @Override
    @DeleteMapping(
            path = "/delete/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(value = "hasAuthority('DELETE_ADMIN')")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        regionService.delete(id);
        return ResponseData.statusResponse(null, HttpStatus.OK, "Successfully deleted a region!");
    }
}