package org.metrodataacademy.TugasSpringBoot.models.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentResponse {

    private Integer id;
    private String name;
    private String location;
    private String manager;
}