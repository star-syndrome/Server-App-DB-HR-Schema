package org.metrodataacademy.TugasSpringBoot.services;

import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.CreateRegionRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.UpdateRegionRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.responses.RegionResponse;

import java.util.List;

public interface RegionService {

    List<RegionResponse> getAllRegions();

    RegionResponse getRegionByID(Integer id);

    RegionResponse createRegion(CreateRegionRequest request);

    RegionResponse updateRegion(Integer id, UpdateRegionRequest request);

    void deleteRegion(Integer id);
}