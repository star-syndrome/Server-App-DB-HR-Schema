package org.metrodataacademy.TugasSpringBoot.services.impls;

import lombok.extern.slf4j.Slf4j;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.ForgotPasswordRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.LoginRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.RegistrationRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.responses.LoginResponse;
import org.metrodataacademy.TugasSpringBoot.models.dtos.responses.UserResponse;
import org.metrodataacademy.TugasSpringBoot.models.entities.Employee;
import org.metrodataacademy.TugasSpringBoot.models.entities.Role;
import org.metrodataacademy.TugasSpringBoot.models.entities.User;
import org.metrodataacademy.TugasSpringBoot.repositories.EmployeeRepository;
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

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class AuthServiceImpl implements
        AuthService<UserResponse, LoginResponse, RegistrationRequest, LoginRequest, ForgotPasswordRequest> {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private RoleServiceImpl roleServiceImpl;

    @Override
    public UserResponse registration(RegistrationRequest registrationRequest) {
        try {
            log.info("Trying to registration employee with name: {}", registrationRequest.getName());
            if (employeeRepository.existsByEmail(registrationRequest.getEmail())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email or phone already exists!");
            } else if (userRepository.existsByUsername(registrationRequest.getUsername())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists!");
            }

            Employee employees = modelMapper.map(registrationRequest, Employee.class);
            User users = modelMapper.map(registrationRequest, User.class);

            users.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));

            List<Role> roles = Collections.singletonList(roleServiceImpl.getById(1));
            users.setRoles(roles);

            users.setEmployee(employees);
            employees.setUser(users);
            employeeRepository.save(employees);

            log.info("Registration employee success, new employee: {}", registrationRequest.getName());
            return UserResponse.builder()
                    .id(employees.getId())
                    .name(employees.getFirstName())
                    .email(employees.getEmail())
                    .phone(employees.getPhoneNumber())
                    .username(employees.getUser().getUsername())
                    .build();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
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

            System.out.println("Successfully sign in, user: " + userDetails.getUsername());
            return LoginResponse.builder()
                    .id(user.getId())
                    .username(userDetails.getUsername())
                    .email(user.getEmployee().getEmail())
                    .roles(roles)
                    .build();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public UserResponse forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        try {
            log.info("Trying to forgot password!");
            User user = userRepository.findByUsername(forgotPasswordRequest.getUsername())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!"));

            if (!forgotPasswordRequest.getNewPassword().equals(forgotPasswordRequest.getRepeatNewPassword())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password not match!");
            }

            String encode = passwordEncoder.encode(forgotPasswordRequest.getNewPassword());
            user.setPassword(encode);
            userRepository.save(user);
            log.info("Forgot password success!");

            return UserResponse.builder()
                    .id(user.getId())
                    .name(user.getEmployee().getFirstName())
                    .email(user.getEmployee().getEmail())
                    .phone(user.getEmployee().getPhoneNumber())
                    .username(user.getUsername())
                    .build();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            throw e;
        }
    }
}