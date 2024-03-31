package org.metrodataacademy.TugasSpringBoot.controllers.impls;

import org.metrodataacademy.TugasSpringBoot.controllers.AuthController;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.LoginRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.requests.RegistrationRequest;
import org.metrodataacademy.TugasSpringBoot.models.dtos.responses.ResponseData;
import org.metrodataacademy.TugasSpringBoot.services.impls.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthControllerImpl implements AuthController<Object, RegistrationRequest, LoginRequest> {

    @Autowired
    private AuthServiceImpl authService;

    @Override
    @PostMapping(
            path = "/registration",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> registration(@Validated @RequestBody RegistrationRequest registrationRequest) {
        return ResponseData.statusResponse(authService.registration(registrationRequest),
                HttpStatus.OK, "Registration employee success!");
    }

    @Override
    @PostMapping(
            path = "/login",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> login(@Validated @RequestBody LoginRequest req) {
        return ResponseData.statusResponse(authService.login(req),
                HttpStatus.OK, "Login success!");
    }
}