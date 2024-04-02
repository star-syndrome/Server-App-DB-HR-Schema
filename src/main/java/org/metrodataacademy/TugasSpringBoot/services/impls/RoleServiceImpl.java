package org.metrodataacademy.TugasSpringBoot.services.impls;

import lombok.extern.slf4j.Slf4j;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.CreateRoleRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.UpdateRoleRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.responses.RoleResponse;
import org.metrodataacademy.TugasSpringBoot.models.entities.Privilege;
import org.metrodataacademy.TugasSpringBoot.models.entities.Role;
import org.metrodataacademy.TugasSpringBoot.repositories.PrivilegeRepository;
import org.metrodataacademy.TugasSpringBoot.repositories.RoleRepository;
import org.metrodataacademy.TugasSpringBoot.services.GenericService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class RoleServiceImpl implements
        GenericService<RoleResponse, Integer, String, CreateRoleRequest, UpdateRoleRequest> {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public List<RoleResponse> getAll() {
        log.info("Successfully getting all roles!");
        return roleRepository.findAll().stream()
                .map(role -> modelMapper.map(role, RoleResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public RoleResponse getById(Integer id) {
        log.info("Getting role data from role id {}", id);
        return roleRepository.findById(id)
                .map(role -> modelMapper.map(role, RoleResponse.class))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found!"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleResponse> search(String name) {
        log.info("Successfully get regions data by method searching!");
        return roleRepository.searchRoleByName(name).stream()
                .map(role -> modelMapper.map(role, RoleResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public RoleResponse create(CreateRoleRequest request) {
        try {
            log.info("Trying to add a new role");
            if (roleRepository.existsByName(request.getName())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role name already exists!");
            }

            List<String> privilege = request.getPrivileges();
            List<Privilege> privileges = new ArrayList<>();

            privilege.forEach(pri -> {
                Privilege privile = privilegeRepository.findByName(pri)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Privilege " + pri + " not found!"));
                privileges.add(privile);
            });

            Role role = Role.builder()
                    .name(request.getName())
                    .privileges(privileges)
                    .build();

            roleRepository.save(role);
            log.info("Adding new role was successful, new role: {}", role.getName());

            return modelMapper.map(role, RoleResponse.class);
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public RoleResponse update(Integer id, UpdateRoleRequest request) {
        try {
            log.info("Trying to update a role");
            Role role = roleRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found!"));

            if (roleRepository.existsByName(request.getName())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role name already exists!");
            }

            List<String> privilege = request.getPrivileges();
            List<Privilege> privileges = new ArrayList<>();

            privilege.forEach(pri -> {
                Privilege privile = privilegeRepository.findByName(pri)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Privilege " + pri + " not found!"));
                privileges.add(privile);
            });

            role.setName(request.getName() == null || request.getName().isEmpty() ? role.getName() : request.getName());
            role.setPrivileges(privileges);
            roleRepository.save(role);
            log.info("Updating role "+ request.getName() + " was successful!");

            return modelMapper.map(role, RoleResponse.class);
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            log.info("Trying to delete a role");
            Role role = roleRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found!"));

            role.getPrivileges().clear();
            roleRepository.deleteById(role.getId());
            log.info("Deleting role with id: " + id + " was successful!");

        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            throw e;
        }
    }
}