package com.example.store.service;

import com.example.store.domain.authentication.Authorities;

public interface AuthService {

    Authorities findByName(String name);

    Authorities save(Authorities authorities);
}
