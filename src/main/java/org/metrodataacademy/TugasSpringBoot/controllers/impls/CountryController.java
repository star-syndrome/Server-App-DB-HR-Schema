package org.metrodataacademy.TugasSpringBoot.controllers.impls;

import org.metrodataacademy.TugasSpringBoot.controllers.GenericController;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.CreateCountryRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.UpdateCountryRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.responses.ResponseData;
import org.metrodataacademy.TugasSpringBoot.services.impls.CountryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/country")
public class CountryController implements
        GenericController<Object, Integer, CreateCountryRequest, UpdateCountryRequest> {

    @Autowired
    private CountryServiceImpl countryService;

    @Override
    @GetMapping(
            path = "/getAll",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> getAll() {
        return ResponseData.statusResponse(countryService.getAll(),
                HttpStatus.OK, "Successfully getting all countries!");
    }

    @Override
    @GetMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> getById(@PathVariable Integer id) {
        return ResponseData.statusResponse(countryService.getById(id),
                HttpStatus.OK, "Successfully getting data country with id " + id + "!");
    }

    @Override
    @PostMapping(
            path = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> create(@Validated @RequestBody CreateCountryRequest request) {
        return ResponseData.statusResponse(countryService.create(request),
                HttpStatus.OK, "Successfully created a new country!");
    }

    @Override
    @PutMapping(
            path = "/update/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> update(@PathVariable Integer id,
                                         @Validated @RequestBody UpdateCountryRequest request) {
        return ResponseData.statusResponse(countryService.update(id, request),
                HttpStatus.OK, "Successfully updated a country!");
    }

    @Override
    @DeleteMapping(
            path = "/delete/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        countryService.delete(id);
        return ResponseData.statusResponse(null, HttpStatus.OK, "Successfully deleted a country!");
    }
}