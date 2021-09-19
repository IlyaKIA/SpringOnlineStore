package com.example.store.service;

import com.example.store.domain.authentication.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    User findByUsername(String username);

    User save(User user);

    Page<User> findAllByPage(Pageable pageRequest);

    void setEnable(String userId, Boolean enable);

}
