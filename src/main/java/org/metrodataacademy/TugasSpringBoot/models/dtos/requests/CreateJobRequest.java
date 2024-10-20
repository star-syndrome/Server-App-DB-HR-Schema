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
public class CreateJobRequest {

    @NotNull
    private Integer id;

    @NotBlank
    @Size(max = 10)
    private String code;

    @NotBlank
    @Size(max = 35)
    private String title;

    private Integer minSalary;
    private Integer maxSalary;
}