package org.metrodataacademy.TugasSpringBoot.controllers;

import org.springframework.http.ResponseEntity;


public interface GenericController<T, ID, NAME, REQ1, REQ2> {

    ResponseEntity<T> getAll();

    ResponseEntity<T> search(NAME name);

    ResponseEntity<T> create(REQ1 req);

    ResponseEntity<T> update(ID id, REQ2 req);

    ResponseEntity<T> getById(ID id);

    ResponseEntity<T> delete(ID id);
}