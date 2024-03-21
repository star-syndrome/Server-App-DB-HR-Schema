package org.metrodataacademy.TugasSpringBoot.services.impls;

import lombok.extern.slf4j.Slf4j;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.CreateRegionRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.UpdateRegionRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.responses.RegionResponse;
import org.metrodataacademy.TugasSpringBoot.models.entities.Region;
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
public class RegionServiceImpl implements
        GenericService<RegionResponse, Integer, String, CreateRegionRequest, UpdateRegionRequest> {

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public List<RegionResponse> getAll() {
        log.info("Successfully getting all regions!");
        return regionRepository.findAll().stream()
                .map(region -> modelMapper.map(region, RegionResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public RegionResponse getById(Integer id) {
        log.info("Getting region data from region id {}", id);
        return regionRepository.findById(id)
                .map(region -> modelMapper.map(region, RegionResponse.class))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Region not found!"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RegionResponse> search(String name) {
        log.info("Successfully get regions data by method searching!");
        return regionRepository.searchRegionByName(name).stream()
                .map(region -> modelMapper.map(region, RegionResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public RegionResponse create(CreateRegionRequest request) {
        try {
            log.info("Trying to add a new region");
            if (regionRepository.existsByName(request.getName())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Region name already exists!");
            }

            Region region = Region.builder()
                    .name(request.getName())
                    .build();

            regionRepository.save(region);
            log.info("Adding new region was successful, new region: {}", region.getName());

            return modelMapper.map(region, RegionResponse.class);
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public RegionResponse update(Integer id, UpdateRegionRequest request) {
        try {
            log.info("Trying to update a region");
            Region region = regionRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Region not found!"));

            if (regionRepository.existsByName(request.getName())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Region name already exists!");
            }

            region.setName(request.getName());
            regionRepository.save(region);
            log.info("Updating region "+ request.getName() + " was successful!");

            return modelMapper.map(region, RegionResponse.class);
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            log.info("Trying to delete a region");
            Region region = regionRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Region not found!"));

            regionRepository.deleteById(region.getId());
            log.info("Deleting region with id: " + id + " was successful!");

        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            throw e;
        }
    }
}