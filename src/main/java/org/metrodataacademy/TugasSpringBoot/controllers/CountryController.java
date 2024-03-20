package org.metrodataacademy.TugasSpringBoot.controllers;

import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.CreateCountryRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.UpdateCountryRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.responses.ResponseData;
import org.metrodataacademy.TugasSpringBoot.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/country")
public class CountryController {

    @Autowired
    private CountryService countryService;

    @GetMapping(
            path = "/getAll",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> getAllCountries() {
        return ResponseData.statusResponse(countryService.getAllCountries(),
                HttpStatus.OK, "Successfully getting all countries!");
    }

    @GetMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> getCountryByID(@PathVariable Integer id) {
        return ResponseData.statusResponse(countryService.getCountryById(id),
                HttpStatus.OK, "Successfully getting data country with id " + id + "!");
    }

    @PostMapping(
            path = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> createRegion(@Validated @RequestBody CreateCountryRequest request) {
        return ResponseData.statusResponse(countryService.createCountry(request),
                HttpStatus.OK, "Successfully created a new country!");
    }

    @PutMapping(
            path = "/update/{code}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> updateCountry(@PathVariable String code,
                                                @Validated @RequestBody UpdateCountryRequest request) {
        return ResponseData.statusResponse(countryService.updateCountry(code, request),
                HttpStatus.OK, "Successfully updated a country!");
    }

    @DeleteMapping(
            path = "/delete/{code}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> deleteCountry(@PathVariable String code) {
        countryService.deleteCountry(code);
        return ResponseData.statusResponse(null, HttpStatus.OK, "Successfully deleted a region!");
    }
}