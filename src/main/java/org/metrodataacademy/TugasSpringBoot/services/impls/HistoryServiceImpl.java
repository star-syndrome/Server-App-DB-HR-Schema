package org.metrodataacademy.TugasSpringBoot.services.impls;

import lombok.extern.slf4j.Slf4j;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.CreateHistoryRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.responses.HistoryResponse;
import org.metrodataacademy.TugasSpringBoot.models.entities.Department;
import org.metrodataacademy.TugasSpringBoot.models.entities.Employee;
import org.metrodataacademy.TugasSpringBoot.models.entities.History;
import org.metrodataacademy.TugasSpringBoot.models.entities.Job;
import org.metrodataacademy.TugasSpringBoot.repositories.DepartmentRepository;
import org.metrodataacademy.TugasSpringBoot.repositories.EmployeeRepository;
import org.metrodataacademy.TugasSpringBoot.repositories.HistoryRepository;
import org.metrodataacademy.TugasSpringBoot.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class HistoryServiceImpl {

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private JobRepository jobRepository;

    public List<HistoryResponse> getAll() {
        log.info("Successfully getting all histories!");
        return historyRepository.findAll().stream()
                .map(this::toHistoryResponse)
                .collect(Collectors.toList());
    }

    public HistoryResponse create(CreateHistoryRequest req) {
        log.info("Create history for employee");

        Employee employee = employeeRepository.findById(req.getEmployee())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found!"));

        Department department = departmentRepository.findById(req.getDepartment())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found!"));

        Job job = jobRepository.findById(req.getJob())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found!"));

        History history = History.builder()
                .startDate(employee.getHireDate())
                .endDate(new Date())
                .employee(employee)
                .department(department)
                .job(job)
                .build();
        historyRepository.save(history);
        log.info("Successfully add a new history!");

        return toHistoryResponse(history);
    }

    public HistoryResponse toHistoryResponse(History history) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return HistoryResponse.builder()
                .id(history.getId())
                .startDate(simpleDateFormat.format(history.getEmployee().getHireDate()))
                .endDate(simpleDateFormat.format(new Date()))
                .employee(history.getEmployee().getFirstName() + " " + history.getEmployee().getLastName())
                .department(history.getDepartment().getName())
                .job(history.getJob().getTitle())
                .build();
    }
}