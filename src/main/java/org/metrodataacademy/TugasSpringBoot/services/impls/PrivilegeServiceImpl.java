package org.metrodataacademy.TugasSpringBoot.services.impls;

import lombok.extern.slf4j.Slf4j;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.CreatePrivilegeRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.UpdatePrivilegeRequest;
import org.metrodataacademy.TugasSpringBoot.models.entities.Privilege;
import org.metrodataacademy.TugasSpringBoot.models.entities.Role;
import org.metrodataacademy.TugasSpringBoot.repositories.PrivilegeRepository;
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
public class PrivilegeServiceImpl implements
        GenericService<Privilege, Integer, String, CreatePrivilegeRequest, UpdatePrivilegeRequest> {

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Privilege> getAll() {
        log.info("Successfully getting all privileges!");
        return privilegeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Privilege getById(Integer id) {
        log.info("Getting privilege data from privilege id {}", id);
        return privilegeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Privilege not found!"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Privilege> search(String name) {
        log.info("Successfully get privileges data by method searching!");
        return privilegeRepository.searchPrivilegeByName(name);
    }

    @Override
    public Privilege create(CreatePrivilegeRequest request) {
        try {
            log.info("Trying to add a new privilege");
            if (privilegeRepository.existsByName(request.getName())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Privilege name already exists!");
            }

            Privilege privilege = Privilege.builder()
                    .name(request.getName())
                    .build();

            log.info("Adding new privilege was successful, new privilege: {}", privilege.getName());

            return privilegeRepository.save(privilege);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public Privilege update(Integer id, UpdatePrivilegeRequest request) {
        try {
            log.info("Trying to update a privilege");
            Privilege privilege = privilegeRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Privilege not found!"));

            if (privilegeRepository.existsByName(request.getName())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Privilege name already exists!");
            }

            privilege.setName(request.getName());

            log.info("Updating privilege {} was successful!", request.getName());

            return privilegeRepository.save(privilege);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public Privilege delete(Integer id) {
        try {
            log.info("Trying to delete a privilege");
            Privilege privilege = privilegeRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Privilege not found!"));

            for (Role role : privilege.getRoles()) {
                role.getPrivileges().remove(privilege);
            }

            privilegeRepository.delete(privilege);
            log.info("Deleting privilege with id: {} was successful!", id);

            return privilege;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            throw e;
        }
    }
}