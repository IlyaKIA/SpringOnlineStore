package com.example.store.service.impl;

import com.example.store.domain.authentication.Authorities;
import com.example.store.domain.authentication.User;
import com.example.store.repository.UserRepository;
import com.example.store.service.AuthService;
import com.example.store.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final AuthService authService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
    }

    @Override
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        System.out.println(user);
        user = userRepository.save(user);
        Authorities authorities = new Authorities(user.getUsername(), "ROLE_USER");
        authorities = authService.save(authorities);
        return user;
    }

    @Override
    public void setEnable(String userId, Boolean enable) {
        User user = userRepository.findByUsername(userId)
                .orElseThrow(EntityNotFoundException::new);

        user.setEnabled(enable);

        userRepository.save(user);
    }

    @Override
    public Page<User> findAllByPage(Pageable pageRequest) {
        return userRepository.findAll(pageRequest);
    }
}
