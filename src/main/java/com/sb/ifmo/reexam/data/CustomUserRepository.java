package com.sb.ifmo.reexam.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomUserRepository extends JpaRepository<CustomUser, Long> {
    CustomUser findByUsernameIs(String username);

    CustomUser findByEmailIs(String name);
}
