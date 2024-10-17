package org.metrodataacademy.TugasSpringBoot.models.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateLocationRequest {

    @NotBlank
    private String city;

    private String streetAddress;
    private String postalCode;
    private String stateProvince;

    @NotNull
    private Integer country_id;
}