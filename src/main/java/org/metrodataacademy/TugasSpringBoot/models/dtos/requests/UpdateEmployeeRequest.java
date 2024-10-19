package org.metrodataacademy.TugasSpringBoot.models.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEmployeeRequest {

    @Size(max = 20)
    private String firstName;

    @NotBlank
    @Size(max = 25)
    private String lastName;

    @Email
    @Size(max = 25)
    private String email;

    @Size(max = 20)
    private String phoneNumber;

    @Size(max = 6)
    private Integer salary;

    private Float commissionPct;

    @NotNull
    private Integer manager_id;

    @NotBlank
    private String job_id;

    @NotNull
    private Integer department_id;
}