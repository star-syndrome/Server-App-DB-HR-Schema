package org.metrodataacademy.TugasSpringBoot.models.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_employee")
public class Employee {

    @Id
    @Column(name = "id", length = 6)
    private Integer id;

    @Column(name = "first_name", length = 20)
    private String firstName;

    @Column(name = "last_name", length = 25, nullable = false)
    private String lastName;

    @Column(name = "email", length = 25, nullable = false, unique = true)
    private String email;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "hire_date", nullable = false)
    private Date hireDate;

    @Column(name = "salary")
    private Integer salary;

    @Column(name = "commission_pct", precision = 8, scale = 2)
    private Float commissionPct;

    @ManyToOne
    @JoinColumn(name = "manager", referencedColumnName = "id")
    private Employee manager;

    @ManyToOne
    @JoinColumn(name = "job", referencedColumnName = "id")
    private Job job;

    @ManyToOne
    @JoinColumn(name = "department", referencedColumnName = "id")
    private Department department;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private User user;

    @OneToMany(mappedBy = "manager", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    List<Employee> employees;

    @OneToMany(mappedBy = "manager", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    List<Department> departments;
}