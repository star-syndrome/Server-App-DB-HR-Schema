package org.metrodataacademy.TugasSpringBoot.services.impls;

import lombok.extern.slf4j.Slf4j;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.CreateDepartmentRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.UpdateDepartmentRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.responses.DepartmentResponse;
import org.metrodataacademy.TugasSpringBoot.models.entities.Department;
import org.metrodataacademy.TugasSpringBoot.models.entities.Employee;
import org.metrodataacademy.TugasSpringBoot.models.entities.Location;
import org.metrodataacademy.TugasSpringBoot.repositories.DepartmentRepository;
import org.metrodataacademy.TugasSpringBoot.repositories.EmployeeRepository;
import org.metrodataacademy.TugasSpringBoot.repositories.LocationRepository;
import org.metrodataacademy.TugasSpringBoot.services.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class DepartmentServiceImpl implements
        GenericService<DepartmentResponse, Integer, String, CreateDepartmentRequest, UpdateDepartmentRequest> {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    @Transactional(readOnly = true)
    public List<DepartmentResponse> getAll() {
        log.info("Successfully getting all departments!");
        return departmentRepository.findAll().stream()
                .map(this::toDepartmentResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public DepartmentResponse getById(Integer id) {
        log.info("Getting department data from job id {}", id);
        return departmentRepository.findById(id)
                .map(this::toDepartmentResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found!"));
    }

    @Override
    public DepartmentResponse create(CreateDepartmentRequest req) {
        log.info("Trying to add a new department");

        Location location = locationRepository.findById(req.getLocation_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found!"));
        Employee employee = employeeRepository.findById(req.getManager_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Manager not found!"));

        Department department = Department.builder()
                .id(req.getId())
                .name(req.getName())
                .location(location)
                .manager(employee)
                .build();
        departmentRepository.save(department);
        log.info("Adding new department was successful!");

        return toDepartmentResponse(department);
    }

    @Override
    public DepartmentResponse update(Integer id, UpdateDepartmentRequest req) {
        log.info("Trying to update department data with id: {}", id);

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found!"));

        Location location = locationRepository.findById(req.getLocation_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found!"));
        Employee employee = employeeRepository.findById(req.getManager_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Manager not found!"));

        department.setName(req.getName());
        department.setLocation(location);
        department.setManager(employee);
        departmentRepository.save(department);
        log.info("Updating department was successful!");

        return toDepartmentResponse(department);
    }

    @Override
    public DepartmentResponse delete(Integer id) {
        log.info("Trying to delete department with id: {}", id);
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found!"));

        departmentRepository.delete(department);
        log.info("Deleting department with id: {} was successful!", id);

        return toDepartmentResponse(department);
    }

    @Override
    public List<DepartmentResponse> search(String string) {
        return null;
    }

    public DepartmentResponse toDepartmentResponse(Department department) {
        return DepartmentResponse.builder()
                .id(department.getId())
                .name(department.getName())
                .location(department.getLocation().getStreetAddress() + ", " + department.getLocation().getCity())
                .manager(department.getManager().getFirstName() + " " + department.getManager().getLastName())
                .build();
    }
}