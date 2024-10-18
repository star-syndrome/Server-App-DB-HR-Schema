package org.metrodataacademy.TugasSpringBoot.services.impls;

import lombok.extern.slf4j.Slf4j;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.CreateEmployeeRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.UpdateEmployeeRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.responses.EmpResponse;
import org.metrodataacademy.TugasSpringBoot.models.entities.Department;
import org.metrodataacademy.TugasSpringBoot.models.entities.Employee;
import org.metrodataacademy.TugasSpringBoot.models.entities.Job;
import org.metrodataacademy.TugasSpringBoot.repositories.DepartmentRepository;
import org.metrodataacademy.TugasSpringBoot.repositories.EmployeeRepository;
import org.metrodataacademy.TugasSpringBoot.repositories.JobRepository;
import org.metrodataacademy.TugasSpringBoot.services.GenericService;
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
@Transactional
@Slf4j
public class EmployeeServiceImpl implements
        GenericService<EmpResponse, Integer, String, CreateEmployeeRequest, UpdateEmployeeRequest> {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    @Transactional(readOnly = true)
    public List<EmpResponse> getAll() {
        log.info("Successfully getting all employees!");
        return employeeRepository.findAll().stream()
                .map(this::toEmployeeResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public EmpResponse getById(Integer id) {
        log.info("Getting employee data from employee id {}", id);
        return employeeRepository.findById(id)
                .map(this::toEmployeeResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found!"));
    }

    @Override
    public EmpResponse create(CreateEmployeeRequest req) {
        log.info("Trying to add a new department");

        if (employeeRepository.existsByEmail(req.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists!");
        }

        Employee manager = employeeRepository.findById(req.getManager_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Manager not found!"));

        Job job = jobRepository.findById(req.getJob_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found!"));

        Department department = departmentRepository.findById(req.getDepartment_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found!"));

        Employee employee = Employee.builder()
                .id(req.getId())
                .firstName(req.getFirstName())
                .lastName(req.getLastName())
                .email(req.getEmail())
                .phoneNumber(req.getPhoneNumber())
                .hireDate(new Date())
                .salary(req.getSalary())
                .commissionPct(req.getCommissionPct())
                .manager(manager)
                .job(job)
                .department(department)
                .build();
        employeeRepository.save(employee);
        log.info("Adding new employee {} successful!", employee.getFirstName());

        return toEmployeeResponse(employee);

    }

    @Override
    public EmpResponse update(Integer id, UpdateEmployeeRequest req) {
        log.info("Trying to update employee data with id: {}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found!"));

        if (employeeRepository.countByEmailForUpdate(req.getEmail(), id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists!");
        }

        Employee manager = employeeRepository.findById(req.getManager_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Manager not found!"));

        Job job = jobRepository.findById(req.getJob_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found!"));

        Department department = departmentRepository.findById(req.getDepartment_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found!"));

        employee.setFirstName(req.getFirstName());
        employee.setLastName(req.getLastName());
        employee.setEmail(req.getEmail());
        employee.setPhoneNumber(req.getPhoneNumber());
        employee.setSalary(req.getSalary());
        employee.setCommissionPct(req.getCommissionPct());
        employee.setManager(manager);
        employee.setJob(job);
        employee.setDepartment(department);
        employeeRepository.save(employee);
        log.info("Updating employee {} was successful!", req.getFirstName());

        return toEmployeeResponse(employee);
    }

    @Override
    public EmpResponse delete(Integer id) {
        log.info("Trying to delete employee with id: {}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found!"));

        employeeRepository.delete(employee);
        log.info("Deleting employee with id: {} was successful!", id);

        return toEmployeeResponse(employee);
    }

    @Override
    public List<EmpResponse> search(String name) {
        return null;
    }

    public EmpResponse toEmployeeResponse(Employee employee) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return EmpResponse.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .phoneNumber(employee.getPhoneNumber())
                .hireDate(simpleDateFormat.format(employee.getHireDate()))
                .salary(employee.getSalary())
                .commissionPct(employee.getCommissionPct())
                .manager(employee.getManager().getFirstName() + " " + employee.getManager().getLastName())
                .job(employee.getJob().getTitle())
                .department(employee.getDepartment().getName())
                .build();
    }
}