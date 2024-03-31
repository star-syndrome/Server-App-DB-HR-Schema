package org.metrodataacademy.TugasSpringBoot.models.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEmployeeRequest {

    @Size(max = 50)
    private String name;

    @Email
    private String email;

    @Size(max = 13)
    private String phone;
}