package org.metrodataacademy.TugasSpringBoot.controllers.impls;

import org.metrodataacademy.TugasSpringBoot.controllers.GenericController;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.CreateCountryRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.UpdateCountryRequest;
import org.metrodataacademy.TugasSpringBoot.services.impls.CountryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/country")
@PreAuthorize(value = "hasAnyRole('ADMIN', 'USER')")
public class CountryControllerImpl implements
        GenericController<Object, Integer, String, CreateCountryRequest, UpdateCountryRequest> {

    @Autowired
    private CountryServiceImpl countryService;

    @Override
    @GetMapping(
            path = "/getAll",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(value = "hasAnyAuthority('READ_USER', 'READ_ADMIN')")
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok().body(countryService.getAll());
    }

    @Override
    @GetMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(value = "hasAuthority('READ_ADMIN')")
    public ResponseEntity<Object> getById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(countryService.getById(id));
    }

    @Override
    @GetMapping(
            path = "/search",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(value = "hasAnyAuthority('READ_USER', 'READ_ADMIN')")
    public ResponseEntity<Object> search(@RequestParam String name) {
        return ResponseEntity.ok().body(countryService.search(name));
    }

    @Override
    @PostMapping(
            path = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(value = "hasAnyAuthority('CREATE_ADMIN')")
    public ResponseEntity<Object> create(@Validated @RequestBody CreateCountryRequest request) {
        return ResponseEntity.ok().body(countryService.create(request));
    }

    @Override
    @PutMapping(
            path = "/update/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(value = "hasAnyAuthority('UPDATE_ADMIN')")
    public ResponseEntity<Object> update(@PathVariable Integer id,
                                         @Validated @RequestBody UpdateCountryRequest request) {
        return ResponseEntity.ok().body(countryService.update(id, request));
    }

    @Override
    @DeleteMapping(
            path = "/delete/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(value = "hasAnyAuthority('DELETE_ADMIN')")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {;
        return ResponseEntity.ok().body(countryService.delete(id));
    }
}