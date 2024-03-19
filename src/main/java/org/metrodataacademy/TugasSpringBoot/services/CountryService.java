package org.metrodataacademy.TugasSpringBoot.services;

import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.CreateCountryRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.UpdateCountryRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.responses.CountryResponse;

import java.util.List;

public interface CountryService {

    List<CountryResponse> getAllCountries();

    CountryResponse getCountryById(Integer id);

    CountryResponse createCountry(CreateCountryRequest request);

    CountryResponse updateCountry(String code, UpdateCountryRequest request);

    void deleteCountry(String code);
}
