package org.metrodataacademy.TugasSpringBoot.services.impls;

import lombok.extern.slf4j.Slf4j;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.LoginRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.RegistrationRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.responses.LoginResponse;
import org.metrodataacademy.TugasSpringBoot.models.dtos.responses.MessageResponse;
import org.metrodataacademy.TugasSpringBoot.models.entities.Employee;
import org.metrodataacademy.TugasSpringBoot.models.entities.Role;
import org.metrodataacademy.TugasSpringBoot.models.entities.User;
import org.metrodataacademy.TugasSpringBoot.repositories.EmployeeRepository;
import org.metrodataacademy.TugasSpringBoot.repositories.RoleRepository;
import org.metrodataacademy.TugasSpringBoot.repositories.UserRepository;
import org.metrodataacademy.TugasSpringBoot.services.AuthService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class AuthServiceImpl implements
        AuthService<MessageResponse, LoginResponse, RegistrationRequest, LoginRequest> {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;


    @Override
    public MessageResponse registration(RegistrationRequest request) {
        try {
            log.info("Trying to registration employee with name: {}", request.getName());
            if (employeeRepository.existsByPhoneOrEmail(request.getPhone(), request.getEmail())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email or phone already exists!");
            } else if (userRepository.existsByUsername(request.getUsername())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists!");
            }

            Employee employees = modelMapper.map(request, Employee.class);
            User users = User.builder()
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .build();

            List<String> roleName = request.getRole();
            List<Role> roles = new ArrayList<>();

            if (roleName == null) {
                Role role = roleRepository.findByName("USER")
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found!"));
                roles.add(role);
            } else {
                roleName.forEach(role -> {
                    Role rol = roleRepository.findByName(role)
                            .orElseThrow(() -> new ResponseStatusException
                                    (HttpStatus.NOT_FOUND, "Role " + role + " is not found!"));
                    roles.add(rol);
                });
            }

            users.setRoles(roles);
            users.setEmployee(employees);
            employees.setUser(users);

            employeeRepository.save(employees);
            log.info("Registration employee success, new employee: {}", request.getName());

            return MessageResponse.builder()
                    .message("Registration employee success, new employee: " + request.getName())
                    .build();
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public LoginResponse login(LoginRequest req) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetails userDetails = userDetailsService.loadUserByUsername(req.getUsername());
            User user = userRepository.findByUsername(req.getUsername())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!"));

            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            log.info("User: " + userDetails.getUsername() + " successfully sign in!");
            return LoginResponse.builder()
                    .id(user.getId())
                    .username(userDetails.getUsername())
                    .email(user.getEmployee().getEmail())
                    .roles(roles)
                    .build();
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            throw e;
        }
    }
}