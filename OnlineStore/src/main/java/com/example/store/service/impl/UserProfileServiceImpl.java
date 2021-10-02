package com.example.store.service.impl;

import com.example.store.domain.authentication.UserProfile;
import com.example.store.repository.UserProfileRepository;
import com.example.store.repository.UserRepository;
import com.example.store.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Optional<UserProfile> findByName(String name) {
        return userProfileRepository.findById(name);
    }

    @Override
    @Transactional
    public UserProfile save(UserProfile userProfile) {
        return null;
    }
}
