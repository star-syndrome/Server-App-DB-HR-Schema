package org.metrodataacademy.TugasSpringBoot.models.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailRequestJMS {

    @Email
    private String recipient;

    private String subject;
    private String npm;
    private String nama;
    private String kelas;
    private String semester;
    private String prodi;
}