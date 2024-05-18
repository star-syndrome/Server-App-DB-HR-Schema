package org.metrodataacademy.TugasSpringBoot.services.impls;

import lombok.extern.slf4j.Slf4j;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.UpdateEmployeeRequest;
import org.metrodataacademy.TugasSpringBoot.models.entities.Employee;
import org.metrodataacademy.TugasSpringBoot.repositories.EmployeeRepository;
import org.metrodataacademy.TugasSpringBoot.services.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
@Slf4j
public class EmployeeServiceImpl implements
        GenericService<Employee, Integer, String, Employee, UpdateEmployeeRequest> {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Employee> getAll() {
        log.info("Successfully getting all employees!");
        return employeeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Employee getById(Integer id) {
        log.info("Getting employee data from employee id {}", id);
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found!"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employee> search(String name) {
        log.info("Successfully get countries data by method searching!");
        return employeeRepository.searchByName(name);
    }


    @Override
    public Employee update(Integer id, UpdateEmployeeRequest req) {
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

            log.info("Updating employee {} was successful!", req.getName());

            return employeeRepository.save(employee);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public Employee delete(Integer id) {
        try {
            log.info("Trying to delete employee with id: {}", id);
            Employee employee = employeeRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found!"));

            employeeRepository.delete(employee);
            log.info("Deleting employee with id: {} was successful!", id);

            return employee;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public Employee create(Employee req) {
        return null;
    }
}