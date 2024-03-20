package org.metrodataacademy.TugasSpringBoot.services.impls;

import lombok.extern.slf4j.Slf4j;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.CreateRegionRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.UpdateRegionRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.responses.RegionResponse;
import org.metrodataacademy.TugasSpringBoot.models.entities.Region;
import org.metrodataacademy.TugasSpringBoot.repositories.RegionRepository;
import org.metrodataacademy.TugasSpringBoot.services.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
@Slf4j
public class RegionServiceImpl implements GenericService<Region, Integer> {

    @Autowired
    private RegionRepository regionRepository;

    private RegionResponse toRegionResponse(Region region) {
        return RegionResponse.builder()
                .id(region.getId())
                .name(region.getName())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Region> getAll() {
        log.info("Successfully getting all regions!");
        return regionRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Region getById(Integer id) {
        log.info("Getting region data from region id {}", id);
        return regionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Region not found!"));
    }

    public RegionResponse createRegion(CreateRegionRequest request) {
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

            return toRegionResponse(region);
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            throw e;
        }
    }

    public RegionResponse updateRegion(Integer id, UpdateRegionRequest request) {
        try {
            log.info("Trying to update a region");
            Region region = regionRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Region not found!"));

            region.setName(request.getName());
            regionRepository.save(region);
            log.info("Updating region was successful!");

            return toRegionResponse(region);
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
            log.info("Deleting region was successful!");

        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            throw e;
        }
    }
}