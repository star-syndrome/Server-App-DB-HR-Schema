package org.metrodataacademy.TugasSpringBoot.services;

import java.util.List;

public interface GenericService<T, ID, REQ1, REQ2> {

    List<T> getAll();

    T create(REQ1 req);

    T update(ID id, REQ2 req);

    T getById(ID id);

    void delete(ID id);
}