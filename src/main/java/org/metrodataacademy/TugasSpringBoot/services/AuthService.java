package org.metrodataacademy.TugasSpringBoot.services;

public interface AuthService<T1, T2, REQ1, REQ2> {

    T1 registration(REQ1 req);

    T2 login(REQ2 req);
}