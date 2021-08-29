package com.chat.repository;

import com.chat.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author
 */
public interface UserDetailsRepository extends JpaRepository<User, String> {
    User findByUsername(String username);
}
