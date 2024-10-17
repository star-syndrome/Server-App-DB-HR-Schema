package org.metrodataacademy.TugasSpringBoot.models.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.metrodataacademy.TugasSpringBoot.models.entities.Location;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateDepartmentRequest {

    @NotNull
    private Integer id;

    @NotBlank
    private String name;

    private Integer location_id;
    private Integer manager_id;
}