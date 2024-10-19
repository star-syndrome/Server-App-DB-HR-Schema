package org.metrodataacademy.TugasSpringBoot.models.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateLocationRequest {

    @NotNull
    @Size(max = 4)
    private Integer id;

    @NotBlank
    @Size(max = 30)
    private String city;

    @Size(max = 40)
    private String streetAddress;

    @Size(max = 12)
    private String postalCode;

    @Size(max = 25)
    private String stateProvince;

    @NotNull
    private Integer country;
}