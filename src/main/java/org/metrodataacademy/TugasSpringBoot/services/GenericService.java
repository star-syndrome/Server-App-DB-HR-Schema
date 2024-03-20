package org.metrodataacademy.TugasSpringBoot.services;

import java.util.List;

public interface GenericService<T, ID> {

    List<T> getAll();

    T getById(ID id);

    void delete(ID id);
}