package org.metrodataacademy.TugasSpringBoot.controllers.impls;

import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.ChangePasswordRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.UpdateUserRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.responses.UserResponse;
import org.metrodataacademy.TugasSpringBoot.services.impls.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
@PreAuthorize(value = "hasAnyRole('USER', 'ADMIN')")
public class UserController {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(value = "hasAnyAuthority('READ_USER', 'READ_ADMIN')")
    public ResponseEntity<UserResponse> getUser() {
        return ResponseEntity.ok()
                .body(userDetailsService.getUser());
    }

    @PutMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(value = "hasAnyAuthority('UPDATE_USER', 'UPDATE_ADMIN')")
    public ResponseEntity<UserResponse> updateUser(@Validated @RequestBody UpdateUserRequest request) {
        return ResponseEntity.ok()
                .body(userDetailsService.updateUser(request));
    }

    @PutMapping(
            path = "/change-password",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(value = "hasAnyAuthority('UPDATE_USER', 'UPDATE_ADMIN')")
    public ResponseEntity<UserResponse> changePassword(@RequestBody ChangePasswordRequest passwordRequest) {
        return ResponseEntity.ok()
                .body(userDetailsService.changePassword(passwordRequest));
    }
}