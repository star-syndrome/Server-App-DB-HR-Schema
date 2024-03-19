package org.metrodataacademy.TugasSpringBoot.models.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCountryRequest {

    @NotBlank
    @Size(max = 2)
    private String code;

    @NotBlank
    @Size(max = 50)
    private String name;

    @NotNull
    private Integer regionId;
}