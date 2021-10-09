package com.example.store.service.impl;

import com.example.store.domain.authentication.UserProfile;
import com.example.store.repository.UserProfileRepository;
import com.example.store.service.UserProfileService;
import com.example.store.util.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;

    @Override
    @Transactional
    public Optional<UserProfile> findByName(String name) {
        return userProfileRepository.findById(name);
    }

    @Override
    @Transactional
    public UserProfile save(UserProfile userProfile, MultipartFile image) {
        UserProfile userProfileEntity = userProfileRepository.save(userProfile);
        if(image != null && !image.isEmpty()) {
            Path imagePath = FileUtils.saveUserImage(image);
            userProfileEntity.setPicturePath(imagePath.toString());
        }
        return userProfileEntity;
    }
}
