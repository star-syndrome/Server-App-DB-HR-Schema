package org.metrodataacademy.TugasSpringBoot.models.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateJobRequest {

    @NotBlank
    @Size(max = 10)
    private String id;

    private String title;
    private Integer minSalary;
    private Integer maxSalary;
}