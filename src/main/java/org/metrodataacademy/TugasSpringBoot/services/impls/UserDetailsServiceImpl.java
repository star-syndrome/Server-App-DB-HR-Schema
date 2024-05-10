package org.metrodataacademy.TugasSpringBoot.services.impls;

import lombok.extern.slf4j.Slf4j;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.ChangePasswordRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.UpdateUserRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.responses.UserResponse;
import org.metrodataacademy.TugasSpringBoot.models.entities.Employee;
import org.metrodataacademy.TugasSpringBoot.models.entities.User;
import org.metrodataacademy.TugasSpringBoot.repositories.EmployeeRepository;
import org.metrodataacademy.TugasSpringBoot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("User with username '%s' not found", username)));
        return new UserDetailsImpl(user);
    }

    public UserResponse getUser() {
        try {
            log.info("Getting information details from employee");
            String username = SecurityContextHolder.getContext().getAuthentication().getName();

            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

            return toUserResponse(user);
        } catch (Exception e) {
            log.error("Error: {}", e.getMessage());
            throw e;
        }
    }

    public UserResponse updateUser(UpdateUserRequest userRequest) {
        try {
            log.info("Trying to update data user!");
            String username = SecurityContextHolder.getContext().getAuthentication().getName();

            Employee employee = employeeRepository.findByUserUsername(username)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!"));

            if (employeeRepository.countByEmailForUpdate(userRequest.getEmail(), employee.getId())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists!");
            }

            if (employeeRepository.countByPhoneForUpdate(userRequest.getPhone(), employee.getId())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Phone number already exists!");
            }

            employee.setName(userRequest.getName());
            employee.setEmail(userRequest.getEmail());
            employee.setPhone(userRequest.getPhone());
            employee.setUser(employee.getUser());
            employeeRepository.save(employee);

            return toUserResponse(employee.getUser());
        } catch (Exception e) {
            log.error("Error : {}", e.getMessage());
            throw e;
        }
    }

    public UserResponse changePassword(ChangePasswordRequest passwordRequest) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();

            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!"));

            log.info("Trying to change password for user: {}", user.getUsername());
            if (!passwordEncoder.matches(passwordRequest.getCurrentPassword(), user.getPassword())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong Password");
            }
            if (!passwordRequest.getNewPassword().equals(passwordRequest.getConfirmationPassword())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong Password");
            }

            user.setPassword(passwordEncoder.encode(passwordRequest.getNewPassword()));
            userRepository.save(user);
            log.info("Successfully changed password!");

            return toUserResponse(user);
        } catch (Exception e) {
            log.error("Error: {}", e.getMessage());
            throw e;
        }
    }

    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getEmployee().getName())
                .email(user.getEmployee().getEmail())
                .phone(user.getEmployee().getPhone())
                .username(user.getUsername())
                .build();
    }
}