package org.metrodataacademy.TugasSpringBoot.controllers;

import org.springframework.http.ResponseEntity;

public interface AuthController<T1, T2, REQ1, REQ2> {

    ResponseEntity<T1> registration(REQ1 req);

    ResponseEntity<T2> login(REQ2 req);
}