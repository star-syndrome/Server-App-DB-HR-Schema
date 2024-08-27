package org.metrodataacademy.TugasSpringBoot.controllers.impls;

import org.metrodataacademy.TugasSpringBoot.controllers.AuthController;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.ForgotPasswordRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.LoginRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.RegistrationRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.responses.LoginResponse;
import org.metrodataacademy.TugasSpringBoot.models.dtos.responses.UserResponse;
import org.metrodataacademy.TugasSpringBoot.services.impls.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:8300", allowCredentials = "true")
public class AuthControllerImpl implements
        AuthController<UserResponse, LoginResponse, RegistrationRequest, LoginRequest, ForgotPasswordRequest> {

    @Autowired
    private AuthServiceImpl authService;

    @Override
    @PostMapping(
            path = "/registration",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<UserResponse> registration(@Validated @RequestBody RegistrationRequest registrationRequest) {
        return ResponseEntity.ok().body(authService.registration(registrationRequest));
    }

    @Override
    @PostMapping(
            path = "/login",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<LoginResponse> login(@Validated @RequestBody LoginRequest req) {
        return ResponseEntity.ok().body(authService.login(req));
    }

    @Override
    @PostMapping(
            path = "/forgot-password",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<UserResponse> forgotPassword(@Validated @RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        return ResponseEntity.ok().body(authService.forgotPassword(forgotPasswordRequest));
    }
}