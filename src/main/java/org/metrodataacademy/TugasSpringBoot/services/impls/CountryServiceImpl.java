package org.metrodataacademy.TugasSpringBoot.services.impls;

import lombok.extern.slf4j.Slf4j;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.CreateCountryRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.UpdateCountryRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.responses.CountryResponse;
import org.metrodataacademy.TugasSpringBoot.models.entities.Country;
import org.metrodataacademy.TugasSpringBoot.models.entities.Region;
import org.metrodataacademy.TugasSpringBoot.repositories.CountryRepository;
import org.metrodataacademy.TugasSpringBoot.repositories.RegionRepository;
import org.metrodataacademy.TugasSpringBoot.services.CountryService;
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
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private RegionRepository regionRepository;

    private CountryResponse toCountryResponse(Country country) {
        return CountryResponse.builder()
                .id(country.getId())
                .code(country.getCode())
                .name(country.getName())
                .regionId(country.getRegion().getId())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CountryResponse> getAllCountries() {
        log.info("Successfully getting all countries!");
        return countryRepository.findAll().stream()
                .map(this::toCountryResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CountryResponse getCountryById(Integer id) {
        log.info("Getting country data from country id {}", id);
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Country Not found!"));

        return toCountryResponse(country);
    }

    @Override
    public CountryResponse createCountry(CreateCountryRequest request) {
        try {
            log.info("Trying to add a new region");
            if (countryRepository.existsByCode(request.getCode())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Country code already exists!");
            }

            Region region = regionRepository.findById(request.getRegionId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Region not found!"));

            Country country = Country.builder()
                    .code(request.getCode())
                    .name(request.getName())
                    .region(region)
                    .build();
            countryRepository.save(country);
            log.info("Adding new country was successful, new country: {}", country.getName());

            return toCountryResponse(country);
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public CountryResponse updateCountry(String code, UpdateCountryRequest request) {
        try {
            log.info("Trying to update country data with code: {}", code);
            Country country = countryRepository.findByCode(code)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Country not found!"));

            country.setCode(request.getCode());
            country.setName(request.getName());
            countryRepository.save(country);
            log.info("Updating country was successful!");

            return toCountryResponse(country);
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void deleteCountry(String code) {
        try {
            log.info("Trying to delete region with code: {}", code);
            Country country = countryRepository.findByCode(code)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Country not found!"));

            countryRepository.delete(country);
            log.info("Deleting country was successful!");

        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            throw e;
        }
    }
}