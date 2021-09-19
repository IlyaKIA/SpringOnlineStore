package com.example.store.service.impl;

import com.example.store.domain.authentication.Authorities;
import com.example.store.repository.AuthRepository;
import com.example.store.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;

    @Override
    public Authorities findByName(String name) {
        return authRepository.findByUsernameIgnoreCase(name)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Authorities save(Authorities authorities) {
        return authRepository.save(authorities);
    }

}
