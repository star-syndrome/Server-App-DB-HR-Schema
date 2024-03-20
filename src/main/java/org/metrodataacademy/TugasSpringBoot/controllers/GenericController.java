package org.metrodataacademy.TugasSpringBoot.controllers;

import org.springframework.http.ResponseEntity;

public interface GenericController<T, ID> {

    ResponseEntity<T> getAll();

    ResponseEntity<T> getById(ID id);

    ResponseEntity<T> delete(ID id);
}