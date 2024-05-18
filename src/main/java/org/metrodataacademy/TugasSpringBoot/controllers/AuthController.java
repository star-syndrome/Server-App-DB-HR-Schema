package org.metrodataacademy.TugasSpringBoot.controllers;

import org.springframework.http.ResponseEntity;

public interface AuthController<T1, T2, REQ1, REQ2, REQ3> {

    ResponseEntity<T1> registration(REQ1 req);

    ResponseEntity<T2> login(REQ2 req);

    ResponseEntity<T1> forgotPassword(REQ3 req);
}