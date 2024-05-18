package org.metrodataacademy.TugasSpringBoot.services;

public interface AuthService<T1, T2, REQ1, REQ2, REQ3> {

    T1 registration(REQ1 req);

    T2 login(REQ2 req);

    T1 forgotPassword(REQ3 req);
}