package com.example.store.service;

import com.example.store.domain.authentication.UserProfile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface UserProfileService {

    Optional<UserProfile> findByName(String name);

    UserProfile save(UserProfile userProfile, MultipartFile image);
}
