package org.metrodataacademy.TugasSpringBoot.services.impls;

import lombok.extern.slf4j.Slf4j;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.CreateLocationRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.UpdateLocationRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.responses.LocationResponse;
import org.metrodataacademy.TugasSpringBoot.models.entities.Country;
import org.metrodataacademy.TugasSpringBoot.models.entities.Location;
import org.metrodataacademy.TugasSpringBoot.repositories.CountryRepository;
import org.metrodataacademy.TugasSpringBoot.repositories.LocationRepository;
import org.metrodataacademy.TugasSpringBoot.services.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class LocationServiceImpl implements
        GenericService<LocationResponse, Integer, String, CreateLocationRequest, UpdateLocationRequest> {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<LocationResponse> getAll() {
        log.info("Successfully getting all countries!");
        return locationRepository.findAll().stream()
                .map(this::toLocationResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public LocationResponse getById(Integer id) {
        log.info("Getting country data from country id {}", id);
        return locationRepository.findById(id)
                .map(this::toLocationResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found!"));
    }

    @Override
    public LocationResponse create(CreateLocationRequest req) {
        log.info("Trying to add a new location");

        Country country = countryRepository.findById(req.getCountry_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Country not found!"));

        Location location = Location.builder()
                .city(req.getCity())
                .streetAddress(req.getStreetAddress())
                .stateProvince(req.getStateProvince())
                .postalCode(req.getPostalCode())
                .country(country)
                .build();
        locationRepository.save(location);
        log.info("Adding new location was successful!");

        return toLocationResponse(location);
    }

    @Override
    public LocationResponse update(Integer id, UpdateLocationRequest req) {
        log.info("Trying to update location data with id: {}", id);

        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found!"));

        Country country = countryRepository.findById(req.getCountry_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Country not found!"));

        location.setCity(req.getCity());
        location.setPostalCode(req.getPostalCode());
        location.setStateProvince(req.getStateProvince());
        location.setStreetAddress(req.getStreetAddress());
        location.setCountry(country);
        locationRepository.save(location);
        log.info("Updating location was successful!");

        return toLocationResponse(location);
    }

    @Override
    public LocationResponse delete(Integer id) {
        log.info("Trying to delete location with id: {}", id);
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found!"));

        locationRepository.delete(location);
        log.info("Deleting location with id: {} was successful!", id);

        return toLocationResponse(location);
    }

    @Override
    public List<LocationResponse> search(String string) {
        return null;
    }

    public LocationResponse toLocationResponse(Location location) {
        return LocationResponse.builder()
                .id(location.getId())
                .city(location.getCity())
                .postalCode(location.getPostalCode())
                .stateProvince(location.getStateProvince())
                .streetAddress(location.getStreetAddress())
                .country_name(location.getCountry().getName())
                .build();
    }
}