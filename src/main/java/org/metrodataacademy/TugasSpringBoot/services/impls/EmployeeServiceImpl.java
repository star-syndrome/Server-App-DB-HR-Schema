package org.metrodataacademy.TugasSpringBoot.services.impls;

import lombok.extern.slf4j.Slf4j;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.UpdateEmployeeRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.responses.EmployeeResponse;
import org.metrodataacademy.TugasSpringBoot.models.entities.Employee;
import org.metrodataacademy.TugasSpringBoot.repositories.EmployeeRepository;
import org.metrodataacademy.TugasSpringBoot.services.GenericService;
import org.modelmapper.ModelMapper;
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
public class EmployeeServiceImpl implements
        GenericService<EmployeeResponse, Integer, String, Employee, UpdateEmployeeRequest> {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeResponse> getAll() {
        log.info("Successfully getting all employees!");
        return employeeRepository.findAll().stream()
                .map(this::toEmployeeResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeResponse getById(Integer id) {
        log.info("Getting employee data from employee id {}", id);
        return employeeRepository.findById(id)
                .map(this::toEmployeeResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found!"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeResponse> search(String name) {
        log.info("Successfully get countries data by method searching!");
        return employeeRepository.searchByName(name).stream()
                .map(this::toEmployeeResponse)
                .collect(Collectors.toList());
    }


    @Override
    public EmployeeResponse update(Integer id, UpdateEmployeeRequest req) {
        try {
            log.info("Trying to update employee data with id: {}", id);
            Employee employee = employeeRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found!"));

            if (employeeRepository.existsByPhoneOrEmail(req.getPhone(), req.getEmail())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Phone or email already exists!");
            }

            employee.setName(req.getName() == null || req.getName().isEmpty() ? employee.getName() : req.getName());
            employee.setEmail(req.getEmail() == null || req.getEmail().isEmpty() ? employee.getEmail() : req.getEmail());
            employee.setPhone(req.getPhone() == null || req.getPhone().isEmpty() ? employee.getPhone() : req.getPhone());
            employeeRepository.save(employee);
            log.info("Updating employee " + req.getName() + " was successful!");

            return toEmployeeResponse(employee);
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            log.info("Trying to delete employee with id: {}", id);
            Employee employee = employeeRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found!"));

            employeeRepository.delete(employee);
            log.info("Deleting employee with id: " + id + " was successful!");

        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            throw e;
        }
    }

    private EmployeeResponse toEmployeeResponse(Employee employee) {
        EmployeeResponse employeeResponse = modelMapper.map(employee, EmployeeResponse.class);
        employeeResponse.setUsername(employee.getUser().getUsername());
        employeeResponse.setPassword(employee.getUser().getPassword());
        employeeResponse.setRoles(employee.getUser().getRoles());
        return employeeResponse;
    }

    @Override
    public EmployeeResponse create(Employee req) {
        return null;
    }
}