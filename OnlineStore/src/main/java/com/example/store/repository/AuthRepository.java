package com.example.store.repository;

import com.example.store.domain.authentication.Authorities;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<Authorities, Long> {

    Optional<Authorities> findByUsernameIgnoreCase(String name);

}
