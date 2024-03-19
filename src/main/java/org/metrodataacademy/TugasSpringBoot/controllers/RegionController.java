package org.metrodataacademy.TugasSpringBoot.controllers;

import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.CreateRegionRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.UpdateRegionRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.responses.ResponseData;
import org.metrodataacademy.TugasSpringBoot.services.impls.RegionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/region")
public class RegionController {

    @Autowired
    private RegionServiceImpl regionService;

    @GetMapping(
            path = "/getAll",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> getAllRegion() {
        return ResponseData.statusResponse(regionService.getAllRegions(),
                HttpStatus.OK, "Successfully getting all regions!");
    }

    @GetMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> getRegionByID(@PathVariable Integer id) {
        return ResponseData.statusResponse(regionService.getRegionByID(id),
                HttpStatus.OK, "Successfully getting data region with id " + id + "!");
    }

    @PostMapping(
            path = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> createRegion(@Validated @RequestBody CreateRegionRequest request) {
        return ResponseData.statusResponse(regionService.createRegion(request),
                HttpStatus.OK, "Successfully created a new region!");
    }

    @PutMapping(
            path = "/update/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> updateRegion(@PathVariable Integer id,
                                               @Validated @RequestBody UpdateRegionRequest request) {
        return ResponseData.statusResponse(regionService.updateRegion(id, request),
                HttpStatus.OK, "Successfully updated a region!");
    }

    @DeleteMapping(
            path = "/delete/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> deleteRegion(@PathVariable Integer id) {
        regionService.deleteRegion(id);
        return ResponseData.statusResponse(null, HttpStatus.OK, "Successfully deleted a region!");
    }
}