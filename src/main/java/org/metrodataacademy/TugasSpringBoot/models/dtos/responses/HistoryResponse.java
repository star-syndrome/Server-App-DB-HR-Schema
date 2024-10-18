package org.metrodataacademy.TugasSpringBoot.models.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoryResponse {

    private Long id;
    private String startDate;
    private String endDate;
    private String department;
    private String employee;
    private String job;
}