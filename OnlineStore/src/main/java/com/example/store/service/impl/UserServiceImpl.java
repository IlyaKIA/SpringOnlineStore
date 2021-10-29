package com.example.store.service.impl;

import com.example.store.domain.authentication.Authorities;
import com.example.store.domain.authentication.User;
import com.example.store.domain.authentication.UserProfile;
import com.example.store.repository.UserProfileRepository;
import com.example.store.repository.UserRepository;
import com.example.store.service.AuthService;
import com.example.store.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
    }

    @Override
    @Transactional
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        user.setUserProfile(new UserProfile(user.getUsername(), "\\data\\images\\user\\blank-profile_640.png"));
//        user.getUserProfile().set .setPicturePath("\\data\\images\\user\\blank-profile_640.png");

        user = userRepository.save(user);
        Authorities authorities = new Authorities(user.getUsername(), "ROLE_USER");
        authorities = authService.save(authorities);
//        UserProfile userProfile = new UserProfile(user.getUsername(),null, null, null, null, "\\data\\images\\user\\blank-profile_640.png" );
//        userProfileRepository.save(userProfile);
        return user;
    }

    @Override
    @Transactional
    public void setEnable(String userId, Boolean enable) {
        User user = userRepository.findByUsername(userId)
                .orElseThrow(EntityNotFoundException::new);

        user.setEnabled(enable);

        userRepository.save(user);
    }

    @Override
    @Transactional
    public Page<User> findAllByPage(Pageable pageRequest) {
        return userRepository.findAll(pageRequest);
    }
}
