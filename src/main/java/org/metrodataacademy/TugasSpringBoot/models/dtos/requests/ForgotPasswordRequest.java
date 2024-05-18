package org.metrodataacademy.TugasSpringBoot.models.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForgotPasswordRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String newPassword;

    @NotBlank
    private String repeatNewPassword;
}