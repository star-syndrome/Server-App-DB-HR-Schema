package org.metrodataacademy.TugasSpringBoot.controllers;

import org.springframework.http.ResponseEntity;

public interface AuthController<T, REQ1, REQ2> {

    ResponseEntity<T> registration(REQ1 req);

    ResponseEntity<T> login(REQ2 req);
}