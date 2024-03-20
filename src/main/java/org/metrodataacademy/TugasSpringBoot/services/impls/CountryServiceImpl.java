package org.metrodataacademy.TugasSpringBoot.services.impls;

import lombok.extern.slf4j.Slf4j;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.CreateCountryRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.UpdateCountryRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.responses.CountryResponse;
import org.metrodataacademy.TugasSpringBoot.models.entities.Country;
import org.metrodataacademy.TugasSpringBoot.repositories.CountryRepository;
import org.metrodataacademy.TugasSpringBoot.services.GenericService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
@Slf4j
public class CountryServiceImpl implements GenericService<Country, Integer> {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private RegionServiceImpl regionService;

    @Autowired
    private ModelMapper modelMapper;

    private CountryResponse toCountryResponse(Country country) {
        return CountryResponse.builder()
                .id(country.getId())
                .code(country.getCode())
                .name(country.getName())
                .regionId(country.getRegion().getId())
                .regionName(country.getRegion().getName())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Country> getAll() {
        log.info("Successfully getting all countries!");
        return countryRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Country getById(Integer id) {
        log.info("Getting country data from country id {}", id);
        return countryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Country Not found!"));
    }

    public Country createCountry(CreateCountryRequest request) {
        try {
            log.info("Trying to add a new region");
            if (countryRepository.existsByCode(request.getCode())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Country code already exists!");
            }

            Country country = modelMapper.map(request, Country.class);
            country.setRegion(regionService.getById(request.getRegionId()));

            log.info("Adding new country was successful, new country: {}", country.getName());
            return countryRepository.save(country);
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            throw e;
        }
    }

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
    public void delete(Integer id) {
        try {
            log.info("Trying to delete country with id: {}", id);
            Country country = getById(id);

            countryRepository.delete(country);
            log.info("Deleting country was successful!");

        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            throw e;
        }
    }
}