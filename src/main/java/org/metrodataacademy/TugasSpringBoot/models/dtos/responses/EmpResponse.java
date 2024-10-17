package org.metrodataacademy.TugasSpringBoot.models.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmpResponse {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Date hireDate;
    private Integer salary;
    private Float commissionPct;
    private String manager;
    private String job;
    private String department;
}