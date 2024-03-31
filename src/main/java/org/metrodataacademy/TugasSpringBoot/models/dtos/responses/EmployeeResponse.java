package org.metrodataacademy.TugasSpringBoot.models.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.metrodataacademy.TugasSpringBoot.models.entities.Role;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse {

    private Integer id;
    private String name;
    private String email;
    private String phone;
    private String username;
    private String password;
    private List<Role> roles;
}