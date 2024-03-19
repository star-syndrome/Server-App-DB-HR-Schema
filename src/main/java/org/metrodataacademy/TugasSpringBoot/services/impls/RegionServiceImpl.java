package org.metrodataacademy.TugasSpringBoot.services.impls;

import lombok.extern.slf4j.Slf4j;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.CreateRegionRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.UpdateRegionRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.responses.RegionResponse;
import org.metrodataacademy.TugasSpringBoot.models.entities.Region;
import org.metrodataacademy.TugasSpringBoot.repositories.RegionRepository;
import org.metrodataacademy.TugasSpringBoot.services.RegionService;
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
public class RegionServiceImpl implements RegionService {

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
    public List<RegionResponse> getAllRegions() {
        log.info("Successfully getting all regions!");
        return regionRepository.findAll().stream()
                .map(this::toRegionResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public RegionResponse getRegionByID(Integer id) {
        log.info("Getting region data from region id {}", id);
        Region region = regionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Region not found!"));

        return toRegionResponse(region);
    }

    @Override
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

    @Override
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
    public void deleteRegion(Integer id) {
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