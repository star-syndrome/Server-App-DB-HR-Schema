package org.metrodataacademy.TugasSpringBoot.services.impls;

import lombok.extern.slf4j.Slf4j;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.CreateCountryRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.UpdateCountryRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.responses.CountryResponse;
import org.metrodataacademy.TugasSpringBoot.models.entities.Country;
import org.metrodataacademy.TugasSpringBoot.models.entities.Region;
import org.metrodataacademy.TugasSpringBoot.repositories.CountryRepository;
import org.metrodataacademy.TugasSpringBoot.repositories.RegionRepository;
import org.metrodataacademy.TugasSpringBoot.services.GenericService;
import org.modelmapper.ModelMapper;
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
public class CountryServiceImpl implements
        GenericService<CountryResponse, Integer, String, CreateCountryRequest, UpdateCountryRequest> {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CountryResponse> getAll() {
        log.info("Successfully getting all countries!");
        return countryRepository.findAll().stream()
                .map(this::toCountryResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CountryResponse getById(Integer id) {
        log.info("Getting country data from country id {}", id);
        return countryRepository.findById(id)
                .map(this::toCountryResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Country not found!"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CountryResponse> search(String name) {
        log.info("Successfully get countries data by method searching!");
        return countryRepository.searchByName(name).stream()
                .map(this::toCountryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CountryResponse create(CreateCountryRequest request) {
        try {
            log.info("Trying to add a new region");
            if (countryRepository.existsByCode(request.getCode())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Country code already exists!");
            }

            if (countryRepository.existsByName(request.getName())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Country name already exists!");
            }

            Region region = regionRepository.findById(request.getRegionId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Region not found!"));

            Country country = modelMapper.map(request, Country.class);
            country.setRegion(region);
            countryRepository.save(country);
            log.info("Adding new country was successful, new country: {}", country.getName());

            return toCountryResponse(country);
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public CountryResponse update(Integer id, UpdateCountryRequest request) {
        try {
            log.info("Trying to update country data with id: {}", id);
            Country country = countryRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Country not found!"));

            if (countryRepository.countByCodeForUpdate(request.getCode(), id)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Country code already exists!");
            }

            if (countryRepository.countByNameForUpdate(request.getName(), id)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Country name already exist!");
            }

            Region region = regionRepository.findById(request.getRegionId())
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Region not found!"));

            country.setCode(request.getCode());
            country.setName(request.getName());
            country.setRegion(region);
            countryRepository.save(country);
            log.info("Updating country " + request.getName() + " was successful!");

            return toCountryResponse(country);
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public CountryResponse delete(Integer id) {
        try {
            log.info("Trying to delete country with id: {}", id);
            Country country = countryRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Country not found!"));

            countryRepository.delete(country);
            log.info("Deleting country with id: " + id + " was successful!");

            return modelMapper.map(country, CountryResponse.class);
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            throw e;
        }
    }

    private CountryResponse toCountryResponse(Country country) {
        CountryResponse countryResponse = modelMapper.map(country, CountryResponse.class);
        countryResponse.setRegionId(country.getRegion().getId());
        countryResponse.setRegionName(country.getRegion().getName());
        return countryResponse;
    }
}