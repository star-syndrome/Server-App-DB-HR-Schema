package org.metrodataacademy.TugasSpringBoot.services;

import java.util.List;

public interface GenericService<T, ID, NAME, REQ1, REQ2> {

    List<T> getAll();

    List<T> search(NAME name);

    T create(REQ1 req);

    T update(ID id, REQ2 req);

    T getById(ID id);

    T delete(ID id);
}