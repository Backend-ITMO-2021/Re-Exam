package com.sb.ifmo.reexam.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomUserRepository extends JpaRepository<CustomUser, Long> {
    Optional<CustomUser> findFirstByUsername(String username);
    Optional<CustomUser> findFirstByEmail(String name);
}
